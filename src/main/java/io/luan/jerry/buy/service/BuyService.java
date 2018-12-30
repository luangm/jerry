package io.luan.jerry.buy.service;

import io.luan.jerry.buy.dto.CreateOrderDTO;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.user.domain.User;

public interface BuyService {

    Order createOrder(CreateOrderDTO request);
}
