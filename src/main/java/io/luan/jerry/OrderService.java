package io.luan.jerry;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class OrderService {

    private final OrderMapper orderMapper;

    private final ItemService itemService;

    @Autowired
    public OrderService(OrderMapper orderMapper, ItemService itemService) {
        this.orderMapper = orderMapper;
        this.itemService = itemService;
    }

    public Order createOrder(long userId, long itemId) {
        var item = itemService.getById(itemId);

        Order order = new Order();
        order.setItemId(itemId);
        order.setUserId(userId);
        order.setTotalFee(item.getPrice());
        orderMapper.insert(order);

        return getById(order.getId());
    }

    public Order getById(long id) {
        return orderMapper.findById(id);
    }

}
