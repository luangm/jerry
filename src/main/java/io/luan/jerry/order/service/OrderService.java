package io.luan.jerry.order.service;

import io.luan.jerry.order.domain.Order;

import java.util.List;

public interface OrderService {

    Order create(Long userId, Long itemId);

    Order findById(Long id);

    List<Order> findAll();

}
