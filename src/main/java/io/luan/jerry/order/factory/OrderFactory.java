package io.luan.jerry.order.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderFactory {

    public Order loadFromDataObject(OrderDO orderDO) {
        var order = new Order();
        order.setId(orderDO.getId());
        order.setBuyerId(orderDO.getBuyerId());
        order.setSellerId(orderDO.getSellerId());
        order.setItemId(orderDO.getItemId());
        order.setQuantity(orderDO.getQuantity());
        order.setTotalFee(orderDO.getTotalFee());
        order.setGmtCreate(orderDO.getGmtCreate());
        order.setGmtModified(orderDO.getGmtModified());
        order.setState(EntityState.Unchanged);
        return order;
    }
}
