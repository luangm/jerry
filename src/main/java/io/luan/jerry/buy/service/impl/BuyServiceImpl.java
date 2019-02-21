package io.luan.jerry.buy.service.impl;

import io.luan.jerry.address.domain.Address;
import io.luan.jerry.address.service.DeliveryAddressService;
import io.luan.jerry.buy.dto.ConfirmOrderResult;
import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderAttributes;
import io.luan.jerry.order.domain.SubOrder;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.payment.service.PaymentService;
import io.luan.jerry.payment.vm.PayVM;
import io.luan.jerry.promotion.service.PromotionService;
import io.luan.jerry.shipment.domain.Shipment;
import io.luan.jerry.shipment.domain.ShipmentMethod;
import io.luan.jerry.shipment.service.ShipmentService;
import io.luan.jerry.shipment.vm.ShipVM;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class BuyServiceImpl implements BuyService {

    private final ItemService itemService;

    private final OrderService orderService;

    private final PromotionService promotionService;

    private final PaymentService paymentService;

    private final ShipmentService shipmentService;

    private final DeliveryAddressService deliveryAddressService;

    private final InventoryService inventoryService;

    @Autowired
    public BuyServiceImpl(ItemService itemService, OrderService orderService, PromotionService promotionService, PaymentService paymentService, ShipmentService shipmentService, DeliveryAddressService deliveryAddressService, InventoryService inventoryService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.promotionService = promotionService;
        this.paymentService = paymentService;
        this.shipmentService = shipmentService;
        this.deliveryAddressService = deliveryAddressService;
        this.inventoryService = inventoryService;
    }

    @Override
    public Order createOrder(OrderDTO request) {
        var order = buildOrder(request);
        order = orderService.save(order);

        var payment = buildPayment(order);
        payment = paymentService.save(payment);

        var buyerId = request.getUserId();
        var defaultAddress = deliveryAddressService.findDefaultByUserId(buyerId);
        if (defaultAddress != null) {
            var shipment = buildShipment(order, defaultAddress.getAddress());
            shipment = shipmentService.save(shipment);
        }

        var reduceSuccess = reduceInventory(request);

        if (reduceSuccess) {
            order.enable();
            orderService.save(order);
        }

        return order;
    }

    @Override
    public ConfirmOrderResult confirmOrder(OrderDTO request) {
        var result = new ConfirmOrderResult();

        var order = this.buildOrder(request);
        result.setOrder(order);

        var payment = this.buildPayment(order);
        result.setPayment(payment);

        var buyerId = request.getUserId();
        var defaultAddress = deliveryAddressService.findDefaultByUserId(buyerId);
        if (defaultAddress != null) {
            var shipment = buildShipment(order, defaultAddress.getAddress());
            result.setShipment(shipment);
        }

        return result;
    }

    @Override
    public Order payOrder(PayVM request) {
        var payment = paymentService.findById(request.getPaymentId());
        payment = paymentService.pay(payment);

        var order = orderService.findById(payment.getOrderId());
        order.setPayStatus(payment.getStatus());
        order = orderService.save(order);

        List<Long> invIds = new ArrayList<>();
        List<Long> quantities = new ArrayList<>();
        for (var subOrder : order.getSubOrders()) {
            quantities.add(subOrder.getQuantity());
            Long skuId = null;
            var skuIdStr = subOrder.getAttribute(OrderAttributes.SKU_ID);
            if (skuIdStr != null) {
                skuId = Long.parseLong(skuIdStr);
            }

            var item = itemService.findById(subOrder.getItemId(), true);
            var sku = item.getSkuById(skuId);
            if (sku != null) {
                invIds.add(sku.getInventoryId());
            } else {
                invIds.add(item.getInventoryId());
            }
        }

        inventoryService.reduceBatch(invIds, quantities);

        return order;
    }

    @Override
    public Order receiveOrder(ShipVM request) {
        var shipment = shipmentService.findById(request.getShipmentId());
        shipment.receive();
        shipment = shipmentService.save(shipment);

        var order = orderService.findById(request.getOrderId());
        order.setShipStatus(shipment.getStatus());
        order = orderService.save(order);

        return order;
    }

    @Override
    public Order shipOrder(ShipVM request) {
        var shipment = shipmentService.findById(request.getShipmentId());
        shipment.ship();
        shipment = shipmentService.save(shipment);

        var order = orderService.findById(request.getOrderId());
        order.setShipStatus(shipment.getStatus());
        order = orderService.save(order);

        return order;
    }

    private Order buildOrder(OrderDTO request) {

        var order = new Order();
        order.setBuyerId(request.getUserId());

        List<Long> itemIds = request.getItemIds();
        var promoMap = promotionService.findByItemIds(itemIds);

        for (var subOrderDTO : request.getOrderLines()) {
            var item = itemService.findById(subOrderDTO.getItemId(), true);
            var sku = item.getSkus().stream().filter(i -> i.getId().equals(subOrderDTO.getSkuId())).findFirst().orElse(null);

            Inventory inventory = null;
            Long originalPrice = item.getPrice();

            if (sku != null) {
                inventory = inventoryService.findById(sku.getInventoryId());
                originalPrice = sku.getPrice();
            } else {
                inventory = inventoryService.findById(item.getInventoryId());
            }

            if (inventory == null || inventory.getAvailable() < subOrderDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient Inventory");
            }


            var newPrice = originalPrice;

            // TODO: Sku Promo
            var promo = promoMap.get(item.getId());
            if (promo != null) {
                newPrice = promo.getNewPrice();
            }

            var discount = originalPrice - newPrice;
            var totalDiscount = discount * subOrderDTO.getQuantity();

            var subOrder = new SubOrder();
            subOrder.setBuyerId(request.getUserId());
            subOrder.setSellerId(item.getUserId());
            subOrder.setItemId(subOrderDTO.getItemId());
            subOrder.setItemPrice(originalPrice);
            subOrder.setItemTitle(item.getTitle());
            subOrder.setItemImgUrl(item.getImgUrl());
            subOrder.setQuantity(subOrderDTO.getQuantity());
            subOrder.setDiscountFee(totalDiscount);

            if (subOrderDTO.getSkuId() != null) {
                subOrder.setAttribute(OrderAttributes.SKU_ID, subOrderDTO.getSkuId() + "");
            }

            order.addSubOrder(subOrder);

            if (order.getSellerId() != null && !order.getSellerId().equals(subOrder.getSellerId())) {
                throw new IllegalArgumentException("Multiple seller is not supported at this time");
            }
            order.setSellerId(subOrder.getSellerId());
        }
        return order;
    }

    private Payment buildPayment(Order order) {
        var payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setTotalFee(order.getTotalFee());
        payment.setPayerId(order.getBuyerId());
        payment.setPayeeId(order.getSellerId());
        payment.setChannelId(1L);
        return payment;
    }

    private Shipment buildShipment(Order order, Address address) {
        var shipment = new Shipment();
        shipment.setOrderId(order.getId());
        shipment.setBuyerId(order.getBuyerId());
        shipment.setSellerId(order.getSellerId());
        shipment.setMethod(ShipmentMethod.Courier);
        shipment.setAddress(address);
        return shipment;
    }

    private boolean reduceInventory(OrderDTO request) {
        List<Long> invIds = new ArrayList<>();
        List<Long> quantities = new ArrayList<>();
        for (var subOrderDTO : request.getOrderLines()) {
            var item = itemService.findById(subOrderDTO.getItemId(), true);
            var sku = item.getSkuById(subOrderDTO.getSkuId());
            if (sku != null) {
                invIds.add(sku.getInventoryId());
            } else {
                invIds.add(item.getInventoryId());
            }
            quantities.add(subOrderDTO.getQuantity());

        }
        return inventoryService.freezeBatch(invIds, quantities);
    }
}
