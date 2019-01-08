package io.luan.jerry.order.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderFactory {

    public Order load(OrderDO orderDO) {
        var order = new Order();
        order.setId(orderDO.getId());
        order.setBuyerId(orderDO.getBuyerId());
        order.setTotalFee(orderDO.getTotalFee());
        order.setGmtCreate(orderDO.getGmtCreate());
        order.setGmtModified(orderDO.getGmtModified());
        order.setState(EntityState.Unchanged);

        List<SubOrder> subOrders = orderDO.getSubOrders().stream().map(this::loadSubOrder).collect(Collectors.toList());
        order.setSubOrders(subOrders);

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
        subOrder.setGmtModified(subOrderDO.getGmtModified());
        subOrder.setState(EntityState.Unchanged);
        return subOrder;
    }
}
