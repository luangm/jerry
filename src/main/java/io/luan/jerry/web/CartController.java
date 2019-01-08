package io.luan.jerry.web;

import io.luan.jerry.cart.service.CartService;
import io.luan.jerry.cart.vm.CartItemVM;
import io.luan.jerry.cart.vm.CartOrderVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public ModelAndView cart() {
        var user = SecurityUtils.getCurrentUser();
        var cart = cartService.getCart(user.getId());

        var cartOrderVM = new CartOrderVM();

        List<CartItemVM> cartItems = new ArrayList<>();
        for (var cartItem : cart.getItems()) {
            cartItems.add(new CartItemVM(cartItem));
        }

        cartOrderVM.setCartItems(cartItems);

        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("user", user);
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("order", cartOrderVM);
        return modelAndView;
    }

}
