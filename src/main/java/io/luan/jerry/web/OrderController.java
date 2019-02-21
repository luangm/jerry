package io.luan.jerry.web;

import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.order.vm.OrderVM;
import io.luan.jerry.payment.service.PaymentService;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.shipment.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    private final ShipmentService shipmentService;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService, ShipmentService shipmentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.shipmentService = shipmentService;
    }

    @GetMapping("/order")
    public ModelAndView orderDetail(@RequestParam(value = "orderId") Long orderId) {
        var user = SecurityUtils.getCurrentUser();
        var order = orderService.findById(orderId);
        var payment = paymentService.findByOrderId(orderId);
        var shipment = shipmentService.findByOrderId(orderId);

        var mav = new ModelAndView("orderDetail");
        mav.addObject("order", new OrderVM(order));
        mav.addObject("payment", payment);
        mav.addObject("shipment", shipment);
        return mav;
    }

}
