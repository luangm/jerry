package io.luan.jerry.buy.service.impl;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import io.luan.jerry.order.service.OrderService;
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
    public Order createOrder(OrderDTO request) {
        var order = this.buildOrder(request);
        return orderService.createOrder(order);
    }

    @Override
    public Order confirmOrder(OrderDTO request) {
        return this.buildOrder(request);
    }

    private Order buildOrder(OrderDTO request) {
        var order = new Order();
        order.setBuyerId(request.getUserId());

        for (var subOrderDTO : request.getSubOrders()) {
            var item = itemService.findById(subOrderDTO.getItemId());
            var subOrder = new SubOrder();
            subOrder.setBuyerId(request.getUserId());
            subOrder.setSellerId(item.getUserId());
            subOrder.setItemId(subOrderDTO.getItemId());
            subOrder.setQuantity(subOrderDTO.getQuantity());
            subOrder.setTotalFee(item.getPrice() * subOrderDTO.getQuantity());
            order.addSubOrder(subOrder);
        }
        return order;
    }
}
