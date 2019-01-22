package io.luan.jerry.web;

import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.order.vm.OrderVM;
import io.luan.jerry.payment.service.PaymentService;
import io.luan.jerry.payment.vm.PayVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PayController {

    private final BuyService buyService;

    private final PaymentService paymentService;

    private final OrderService orderService;

    @Autowired
    public PayController(BuyService buyService, PaymentService paymentService, OrderService orderService) {
        this.buyService = buyService;
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping("/pay")
    public ModelAndView pay(@RequestParam("orderId") Long orderId) {
        var user = SecurityUtils.getCurrentUser();

        var order = orderService.findById(orderId);
        var payment = paymentService.findByOrderId(orderId);

        var mav = new ModelAndView("pay");
        mav.addObject("order", new OrderVM(order));
        mav.addObject("payment", payment);
        mav.addObject("request", new PayVM());
        return mav;
    }

    @PostMapping("/payProcess")
    public ModelAndView payProcess(@ModelAttribute PayVM request) {
        var user = SecurityUtils.getCurrentUser();

        if (request.getAction() != null && "pay".equals(request.getAction())) {
            var order = buyService.payOrder(request);
        }

        var mav = new ModelAndView("redirect:/buySuccess");
        mav.addObject("orderId", request.getOrderId());
        return mav;
    }
}
