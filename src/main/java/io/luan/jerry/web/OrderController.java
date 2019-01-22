package io.luan.jerry.web;

import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.order.vm.OrderVM;
import io.luan.jerry.payment.domain.PaymentStatus;
import io.luan.jerry.payment.service.PaymentService;
import io.luan.jerry.payment.vm.PayVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping("/order")
    public ModelAndView orderDetail(@RequestParam(value = "orderId") Long orderId) {
        var user = SecurityUtils.getCurrentUser();
        var order = orderService.findById(orderId);
        var payment = paymentService.findByOrderId(orderId);

        var mav = new ModelAndView("orderDetail");
        mav.addObject("order", new OrderVM(order));
        mav.addObject("payment", payment);
        mav.addObject("shouldPay", payment.getStatus() == PaymentStatus.Created);
        return mav;
    }

}
