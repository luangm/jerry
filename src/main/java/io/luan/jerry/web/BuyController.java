package io.luan.jerry.web;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.dto.SubOrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.buy.vm.ConfirmOrderVM;
import io.luan.jerry.cart.vm.CartOrderVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuyController {

    private final BuyService buyService;

    @Autowired
    public BuyController(BuyService buyService) {
        this.buyService = buyService;
    }

    @PostMapping("/buy")
    public ModelAndView buyProcess(@ModelAttribute ConfirmOrderVM request) {
        var user = SecurityUtils.getCurrentUser();

        var orderDTO = new OrderDTO();
        orderDTO.setUserId(user.getId());
        orderDTO.setSource("cart");
        for (var subOrder : request.getSubOrders()) {
            var subOrderDTO = new SubOrderDTO(subOrder.getItemId(), subOrder.getQuantity());
            orderDTO.getSubOrders().add(subOrderDTO);
        }

        var order = buyService.createOrder(orderDTO);

        var mav = new ModelAndView("buySuccess");
        mav.addObject("order", order);
        return mav;
    }

    @GetMapping(value = "/confirmOrder", params = "buyNow")
    public ModelAndView confirmOrder(@RequestParam("itemId") Long itemId) {
        var user = SecurityUtils.getCurrentUser();

        var orderDTO = new OrderDTO();
        orderDTO.setUserId(user.getId());
        orderDTO.setSource("buyNow");
        var subOrderDTO = new SubOrderDTO(itemId, 1);
        orderDTO.getSubOrders().add(subOrderDTO);

        var order = buyService.confirmOrder(orderDTO);
        var confirmOrderVM = new ConfirmOrderVM(order);

        var mav = new ModelAndView("confirmOrder");
        mav.addObject("user", user);
        mav.addObject("order", confirmOrderVM);
        return mav;
    }

    @PostMapping(value = "/confirmOrder", params = "cart")
    public ModelAndView confirmOrder(@ModelAttribute CartOrderVM request) {
        var user = SecurityUtils.getCurrentUser();

        var orderDTO = new OrderDTO();
        orderDTO.setUserId(user.getId());
        orderDTO.setSource("cart");

        for (var cartItem : request.getCartItems()) {
            if (cartItem.getSelected() != null && cartItem.getSelected()) {
                var subOrderDTO = new SubOrderDTO(cartItem.getItemId(), cartItem.getQuantity());
                orderDTO.getSubOrders().add(subOrderDTO);
            }
        }

        var order = buyService.confirmOrder(orderDTO);
        var confirmOrderVM = new ConfirmOrderVM(order);

        var mav = new ModelAndView("confirmOrder");
        mav.addObject("user", user);
        mav.addObject("order", confirmOrderVM);

        return mav;
    }
}
