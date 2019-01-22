package io.luan.jerry.buy.service.impl;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.payment.service.PaymentService;
import io.luan.jerry.payment.vm.PayVM;
import io.luan.jerry.promotion.service.PromotionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class BuyServiceImpl implements BuyService {

    private final ItemService itemService;

    private final OrderService orderService;

    private final PromotionService promotionService;

    private final PaymentService paymentService;

    @Autowired
    public BuyServiceImpl(ItemService itemService, OrderService orderService, PromotionService promotionService, PaymentService paymentService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.promotionService = promotionService;
        this.paymentService = paymentService;
    }

    @Override
    public Order createOrder(OrderDTO request) {
        var order = buildOrder(request);
        order = orderService.save(order);

        var payment = buildPayment(order);
        payment = paymentService.save(payment);

        return order;
    }

    @Override
    public Order confirmOrder(OrderDTO request) {
        return this.buildOrder(request);
    }

    @Override
    public Order payOrder(PayVM request) {
        var payment = paymentService.findById(request.getPaymentId());
        payment = paymentService.pay(payment);

        var order = orderService.findById(payment.getOrderId());
        order.setPayStatus(payment.getStatus());
        order = orderService.save(order);

        return order;
    }

    private Order buildOrder(OrderDTO request) {
        var order = new Order();
        order.setBuyerId(request.getUserId());

        List<Long> itemIds = request.getItemIds();
        var promoMap = promotionService.findByItemIds(itemIds);

        for (var subOrderDTO : request.getOrderLines()) {
            var item = itemService.findById(subOrderDTO.getItemId());
            var promo = promoMap.get(item.getId());
            var price = item.getPrice();
            if (promo != null) {
                price = promo.getNewPrice();
            }
            var discount = item.getPrice() - price;
            var totalDiscount = discount * subOrderDTO.getQuantity();

            var subOrder = new SubOrder();
            subOrder.setBuyerId(request.getUserId());
            subOrder.setSellerId(item.getUserId());
            subOrder.setItemId(subOrderDTO.getItemId());
            subOrder.setItemPrice(item.getPrice());
            subOrder.setItemTitle(item.getTitle());
            subOrder.setItemImgUrl(item.getImgUrl());
            subOrder.setQuantity(subOrderDTO.getQuantity());
            subOrder.setDiscountFee(totalDiscount);
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
}
