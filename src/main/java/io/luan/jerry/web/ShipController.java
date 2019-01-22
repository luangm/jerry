package io.luan.jerry.web;

import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.order.vm.OrderVM;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.shipment.service.ShipmentService;
import io.luan.jerry.shipment.vm.ShipVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShipController {

    private final BuyService buyService;

    private final ShipmentService shipmentService;

    private final OrderService orderService;

    @Autowired
    public ShipController(BuyService buyService, ShipmentService shipmentService, OrderService orderService) {
        this.buyService = buyService;
        this.shipmentService = shipmentService;
        this.orderService = orderService;
    }

    @PostMapping("/ship")
    public ModelAndView ship(@RequestParam("orderId") Long orderId) {
        var user = SecurityUtils.getCurrentUser();

        var order = orderService.findById(orderId);
        var shipment = shipmentService.findByOrderId(orderId);

        var mav = new ModelAndView("ship");
        mav.addObject("order", new OrderVM(order));
        mav.addObject("shipment", shipment);
        mav.addObject("request", new ShipVM());
        return mav;
    }

    @PostMapping("/receive")
    public ModelAndView receive(@ModelAttribute ShipVM request) {
        var user = SecurityUtils.getCurrentUser();

        var order = buyService.receiveOrder(request);

        var mav = new ModelAndView("redirect:/order");
        mav.addObject("orderId", request.getOrderId());
        return mav;
    }

    @PostMapping("/shipProcess")
    public ModelAndView payProcess(@ModelAttribute ShipVM request) {
        var user = SecurityUtils.getCurrentUser();

        var order = buyService.shipOrder(request);

        var mav = new ModelAndView("redirect:/order");
        mav.addObject("orderId", request.getOrderId());
        return mav;
    }
}
