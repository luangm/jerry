package io.luan.jerry.web;

import io.luan.jerry.cart.domain.CartItem;
import io.luan.jerry.cart.dto.CartItemDTO;
import io.luan.jerry.cart.service.CartService;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final ItemService itemService;

    private final OrderService orderService;

    private final CategoryService categoryService;

    private final CartService cartService;

    @Autowired
    public IndexController(ItemService itemService, OrderService orderService, CategoryService categoryService, CartService cartService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        var user = SecurityUtils.getCurrentUser();
        List<CartItem> cartItems = new ArrayList<>();
        if (user != null) {
            var cart = cartService.getCart(user.getId());
            cartItems = cart.getItems();
        }
        var items = itemService.findAll();
        var orders = orderService.findAll();
        var categories = categoryService.findAll();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", user);
        modelAndView.addObject("items", items);
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("cartRequest", new CartItemDTO());
        return modelAndView;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

}
