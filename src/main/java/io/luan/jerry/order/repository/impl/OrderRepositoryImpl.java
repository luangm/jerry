package io.luan.jerry.order.repository.impl;

import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.data.OrderMapper;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderRepositoryImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public boolean delete(Order order) {
        var orderDO = OrderDO.fromEntity(order);
        if (orderDO.getId() != null) {
            int count = orderMapper.delete(orderDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        for (OrderDO orderDO : orderMapper.findAll()) {
            orders.add(orderDO.toEntity());
        }
        return orders;
    }

    @Override
    public Order findById(Long id) {
        var orderDO = orderMapper.findById(id);
        if (orderDO != null) {
            return orderDO.toEntity();
        }
        return null;
    }

    @Override
    public Order save(Order order) {
        var orderDO = OrderDO.fromEntity(order);
        if (orderDO.getId() == null) {
            orderMapper.insert(orderDO);
            return findById(orderDO.getId());
        } else {
            return order;
        }
    }
}
