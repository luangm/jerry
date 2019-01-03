package io.luan.jerry.order.repository.impl;

import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.data.OrderMapper;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.factory.OrderFactory;
import io.luan.jerry.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderFactory orderFactory;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderRepositoryImpl(OrderMapper orderMapper, OrderFactory orderFactory) {
        this.orderMapper = orderMapper;
        this.orderFactory = orderFactory;
    }

    @Override
    public boolean delete(Order order) {
        var orderDO = new OrderDO(order);
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
            orders.add(orderFactory.loadFromDataObject(orderDO));
        }
        return orders;
    }

    @Override
    public Order findById(Long id) {
        var orderDO = orderMapper.findById(id);
        if (orderDO != null) {
            return orderFactory.loadFromDataObject(orderDO);
        }
        return null;
    }

    @Override
    public void save(Order order) {
        var orderDO = new OrderDO(order);
        if (orderDO.getId() == null) {
            orderMapper.insert(orderDO);
            order.setId(orderDO.getId());
        }
    }
}
