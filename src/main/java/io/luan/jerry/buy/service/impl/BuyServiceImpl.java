package io.luan.jerry.buy.service.impl;

import io.luan.jerry.buy.dto.CreateOrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.user.domain.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class BuyServiceImpl implements BuyService {

    private final ItemService itemService;

    private final OrderService orderService;

    @Autowired
    public BuyServiceImpl(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @Override
    public Order createOrder(CreateOrderDTO request) {
        var item = itemService.findById(request.getItemId());

        var order = new Order();
        order.setItemId(request.getItemId());
        order.setAmount(request.getAmount());
        order.setBuyerId(request.getUserId());
        order.setSellerId(item.getUserId());

        var totalFee = item.getPrice() * request.getAmount();
        order.setTotalFee(totalFee);

        return orderService.createOrder(order);
    }
}
