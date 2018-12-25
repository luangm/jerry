package io.luan.jerry.order.service.impl;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.repository.OrderRepository;
import io.luan.jerry.order.service.OrderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ItemService itemService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public Order create(Long userId, Long itemId) {
        var item = itemService.findById(itemId);

        var order = new Order();
        order.setItemId(itemId);
        order.setBuyerId(userId);
        order.setSellerId(item.getUserId());
        order.setTotalFee(item.getPrice());

        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}
