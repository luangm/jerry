package io.luan.jerry.web;

import io.luan.jerry.cart.dto.CartItemDTO;
import io.luan.jerry.cart.service.CartService;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ModelAndView addToCart(@ModelAttribute @Validated CartItemDTO request) {
        var user = SecurityUtils.getCurrentUser();
        request.setUserId(user.getId());
        cartService.addToCart(request);

        var mav = new ModelAndView("redirect:/index");
        return mav;
    }

    @GetMapping("/cart")
    public ModelAndView cart() {
        var user = SecurityUtils.getCurrentUser();
        var cart = cartService.getCart(user.getId());

        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("user", user);
        modelAndView.addObject("cartItems", cart.getItems());
        modelAndView.addObject("cartRequest", new CartItemDTO());
        return modelAndView;
    }

    @PostMapping("/cart/change")
    public ModelAndView change(@ModelAttribute @Validated CartItemDTO request, @ModelAttribute("redirectUrl") String redirectUrl) {
        var user = SecurityUtils.getCurrentUser();
        request.setUserId(user.getId());
        cartService.update(request);

        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = "/index";
        }

        var mav = new ModelAndView("redirect:" + redirectUrl);
        return mav;
    }

    @PostMapping("/cart/remove")
    public ModelAndView remove(@ModelAttribute @Validated CartItemDTO request, @ModelAttribute("redirectUrl") String redirectUrl) {
        var user = SecurityUtils.getCurrentUser();
        request.setUserId(user.getId());
        cartService.remove(request);

        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = "/index";
        }

        var mav = new ModelAndView("redirect:" + redirectUrl);
        return mav;
    }
}
