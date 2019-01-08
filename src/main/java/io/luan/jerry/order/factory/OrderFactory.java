package io.luan.jerry.order.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderFactory {

    public Order loadFromDataObject(OrderDO orderDO) {
        var order = new Order();
        order.setId(orderDO.getId());
        order.setBuyerId(orderDO.getBuyerId());
//        order.setSellerId(orderDO.getSellerId());
//        order.setItemId(orderDO.getItemId());
//        order.setQuantity(orderDO.getQuantity());
        order.setTotalFee(orderDO.getTotalFee());
        order.setGmtCreate(orderDO.getGmtCreate());
        order.setGmtModified(orderDO.getGmtModified());
        order.setState(EntityState.Unchanged);

        List<SubOrder> subOrders = new ArrayList<>();
        for (var subOrderDO : orderDO.getSubOrders()) {
            subOrders.add(loadSubOrderFromDataObject(subOrderDO));
        }
        order.setSubOrders(subOrders);

        return order;
    }

    private SubOrder loadSubOrderFromDataObject(OrderDO orderDO) {
        var subOrder = new SubOrder();
        subOrder.setId(orderDO.getId());
        subOrder.setParentId(orderDO.getParentId());
        subOrder.setBuyerId(orderDO.getBuyerId());
        subOrder.setSellerId(orderDO.getSellerId());
        subOrder.setItemId(orderDO.getItemId());
        subOrder.setQuantity(orderDO.getQuantity());
        subOrder.setTotalFee(orderDO.getTotalFee());
        subOrder.setGmtCreate(orderDO.getGmtCreate());
        subOrder.setGmtModified(orderDO.getGmtModified());
        subOrder.setState(EntityState.Unchanged);
        return subOrder;
    }
}
