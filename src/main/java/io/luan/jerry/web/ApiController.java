package io.luan.jerry.web;

import io.luan.jerry.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    private final OrderService orderService;

    @Autowired
    public ApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/order/get")
    public Object get(Long id) {
        var order = orderService.findById(id);
        return order;
    }

    @PostMapping("/api/test")
    public Object get(@RequestBody HashMap<String, Object> params) {
        return params;
    }
}
