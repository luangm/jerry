package io.luan.jerry.order.service;

import io.luan.jerry.order.domain.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    Order findById(Long id);

    List<Order> findAll();

}
