package io.luan.jerry.order.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderLine;
import io.luan.jerry.order.domain.OrderState;
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
        order.setOrderLines(orderDO.getOrderLines().stream().map(this::loadOrderLine).collect(Collectors.toList()));
        order.setGmtCreate(orderDO.getGmtCreate());
        order.setAttributes(MapUtils.decodeStringString(orderDO.getAttributes()));

        // Note: GmtModified and State should ALWAYS be set last in that order
        order.setGmtModified(orderDO.getGmtModified());
        order.setState(EntityState.Unchanged);
        return order;
    }

    private OrderLine loadOrderLine(OrderDO orderLineDO) {
        var orderLine = new OrderLine();
        orderLine.setId(orderLineDO.getId());
        orderLine.setParentId(orderLineDO.getParentId());
        orderLine.setBuyerId(orderLineDO.getBuyerId());
        orderLine.setSellerId(orderLineDO.getSellerId());
        orderLine.setItemId(orderLineDO.getItemId());
        orderLine.setItemPrice(orderLineDO.getItemPrice());
        orderLine.setItemTitle(orderLineDO.getItemTitle());
        orderLine.setItemImgUrl(orderLineDO.getItemImgUrl());
        orderLine.setQuantity(orderLineDO.getQuantity());
        orderLine.setDiscountFee(orderLineDO.getDiscountFee());
        orderLine.setGmtCreate(orderLineDO.getGmtCreate());
        orderLine.setStatus(OrderState.fromValue(orderLineDO.getStatus()));
        orderLine.setPayStatus(PaymentState.fromValue(orderLineDO.getPayStatus()));
        orderLine.setShipStatus(ShipmentState.fromValue(orderLineDO.getShipStatus()));
        orderLine.setAttributes(MapUtils.decodeStringString(orderLineDO.getAttributes()));

        // Note: GmtModified and State should ALWAYS be set last in that order
        orderLine.setGmtModified(orderLineDO.getGmtModified());
        orderLine.setState(EntityState.Unchanged);
        return orderLine;
    }
}
