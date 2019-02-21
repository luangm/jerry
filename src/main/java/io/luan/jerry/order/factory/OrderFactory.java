package io.luan.jerry.order.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderState;
import io.luan.jerry.order.domain.SubOrder;
import io.luan.jerry.payment.domain.PaymentState;
import io.luan.jerry.shipment.domain.ShipmentState;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderFactory {

    public Order load(OrderDO orderDO) {
        var order = new Order();
        order.setId(orderDO.getId());
        order.setBuyerId(orderDO.getBuyerId());
        order.setSellerId(orderDO.getSellerId());
        order.setTotalFee(orderDO.getTotalFee());
        order.setStatus(OrderState.fromValue(orderDO.getStatus()));
        order.setPayStatus(PaymentState.fromValue(orderDO.getPayStatus()));
        order.setShipStatus(ShipmentState.fromValue(orderDO.getShipStatus()));
        order.setSubOrders(orderDO.getSubOrders().stream().map(this::loadSubOrder).collect(Collectors.toList()));
        order.setGmtCreate(orderDO.getGmtCreate());
        order.setAttributes(MapUtils.decodeStringString(orderDO.getAttributes()));

        // Note: GmtModified and State should ALWAYS be set last in that order
        order.setGmtModified(orderDO.getGmtModified());
        order.setState(EntityState.Unchanged);
        return order;
    }

    private SubOrder loadSubOrder(OrderDO subOrderDO) {
        var subOrder = new SubOrder();
        subOrder.setId(subOrderDO.getId());
        subOrder.setParentId(subOrderDO.getParentId());
        subOrder.setBuyerId(subOrderDO.getBuyerId());
        subOrder.setSellerId(subOrderDO.getSellerId());
        subOrder.setItemId(subOrderDO.getItemId());
        subOrder.setItemPrice(subOrderDO.getItemPrice());
        subOrder.setItemTitle(subOrderDO.getItemTitle());
        subOrder.setItemImgUrl(subOrderDO.getItemImgUrl());
        subOrder.setQuantity(subOrderDO.getQuantity());
        subOrder.setDiscountFee(subOrderDO.getDiscountFee());
        subOrder.setGmtCreate(subOrderDO.getGmtCreate());
        subOrder.setStatus(OrderState.fromValue(subOrderDO.getStatus()));
        subOrder.setPayStatus(PaymentState.fromValue(subOrderDO.getPayStatus()));
        subOrder.setShipStatus(ShipmentState.fromValue(subOrderDO.getShipStatus()));
        subOrder.setAttributes(MapUtils.decodeStringString(subOrderDO.getAttributes()));

        // Note: GmtModified and State should ALWAYS be set last in that order
        subOrder.setGmtModified(subOrderDO.getGmtModified());
        subOrder.setState(EntityState.Unchanged);
        return subOrder;
    }
}
