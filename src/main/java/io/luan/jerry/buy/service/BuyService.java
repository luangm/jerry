package io.luan.jerry.buy.service;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.order.domain.Order;

public interface BuyService {

    /**
     * Using the request, create an Order and Save to db
     */
    Order createOrder(OrderDTO request);

    /**
     * Using the request, create an Order Entity, but not save it
     */
    Order confirmOrder(OrderDTO request);
}