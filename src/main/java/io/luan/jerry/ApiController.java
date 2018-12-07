package io.luan.jerry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiController {

    private final OrderService orderService;

    @Autowired
    public ApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/order/get")
    public Object get(Long id) {
        var order = orderService.getById(id);
        return order;
    }
}
