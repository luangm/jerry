package io.luan.jerry.web;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.dto.OrderLineDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.buy.vm.ConfirmOrderVM;
import io.luan.jerry.cart.vm.CartOrderVM;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.order.vm.OrderVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BuyController {

    private final BuyService buyService;

    private final OrderService orderService;

    @Autowired
    public BuyController(BuyService buyService, OrderService orderService) {
        this.buyService = buyService;
        this.orderService = orderService;
    }

    @PostMapping("/buy")
    public ModelAndView buyProcess(HttpServletRequest request, @ModelAttribute ConfirmOrderVM confirmOrderVM) {
        var user = SecurityUtils.getCurrentUser();

        var orderDTO = new OrderDTO();
        orderDTO.setUserId(user.getId());
        orderDTO.setSource("cart");
        orderDTO.setAddress(confirmOrderVM.getAddress());
        for (var orderLineVM : confirmOrderVM.getOrderLines()) {
            var orderLineDTO = new OrderLineDTO(orderLineVM.getItemId(), orderLineVM.getSkuId(), orderLineVM.getQuantity());
            orderDTO.getOrderLines().add(orderLineDTO);
        }

        var order = buyService.createOrder(orderDTO);

        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

        var mav = new ModelAndView("redirect:/pay");
        mav.addObject("orderId", order.getId());
        return mav;
    }

    @GetMapping("/buySuccess")
    public ModelAndView buySuccess(@RequestParam("orderId") Long orderId) {
        var user = SecurityUtils.getCurrentUser();

        var order = orderService.findById(orderId);
        var mav = new ModelAndView("buySuccess");
        mav.addObject("order", new OrderVM(order));
        return mav;
    }

    @GetMapping(value = "/confirmOrder", params = "buyNow")
    public ModelAndView confirmOrder(@RequestParam("itemId") Long itemId,
                                     @RequestParam(value = "skuId", required = false) Long skuId,
                                     @RequestParam(value = "quantity", required = false) Long quantity) {
        var user = SecurityUtils.getCurrentUser();

        var orderDTO = new OrderDTO();
        orderDTO.setUserId(user.getId());
        orderDTO.setSource("buyNow");
        if (quantity == null) {
            quantity = 1L;
        }
        var orderLineDTO = new OrderLineDTO(itemId, skuId, quantity);
        orderDTO.getOrderLines().add(orderLineDTO);

        try {
            var order = buyService.confirmOrder(orderDTO);
            var confirmOrderVM = new ConfirmOrderVM(order);

            var mav = new ModelAndView("confirmOrder");
            mav.addObject("user", user);
            mav.addObject("order", confirmOrderVM);

            return mav;
        } catch (IllegalArgumentException e) {
            var mav = new ModelAndView("confirmOrderError");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @PostMapping(value = "/confirmOrder", params = "cart")
    public ModelAndView confirmOrder(@ModelAttribute CartOrderVM request) {
        var user = SecurityUtils.getCurrentUser();

        var orderDTO = new OrderDTO();
        orderDTO.setUserId(user.getId());
        orderDTO.setSource("cart");

        for (var cartItem : request.getCartItems()) {
            if (cartItem.getSelected() != null && cartItem.getSelected()) {
                var orderLineDTO = new OrderLineDTO(cartItem.getItemId(), cartItem.getSkuId(), cartItem.getQuantity());
                orderDTO.getOrderLines().add(orderLineDTO);
            }
        }

        try {
            var order = buyService.confirmOrder(orderDTO);
            var confirmOrderVM = new ConfirmOrderVM(order);

            var mav = new ModelAndView("confirmOrder");
            mav.addObject("user", user);
            mav.addObject("order", confirmOrderVM);

            return mav;
        } catch(IllegalArgumentException e) {
            var mav = new ModelAndView("confirmOrderError");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

}
