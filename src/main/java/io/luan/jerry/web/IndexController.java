package io.luan.jerry.web;

import io.luan.jerry.cart.domain.CartItem;
import io.luan.jerry.cart.dto.CartItemDTO;
import io.luan.jerry.cart.service.CartService;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.promotion.service.PromotionService;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    private final ItemService itemService;

    private final OrderService orderService;

    private final CategoryService categoryService;

    private final CartService cartService;

    private final PromotionService promotionService;

    @Autowired
    public IndexController(ItemService itemService, OrderService orderService, CategoryService categoryService, CartService cartService, PromotionService promotionService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.promotionService = promotionService;
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        var user = SecurityUtils.getCurrentUser();

        var items = itemService.findAll();
        var orders = orderService.findAll();
        var categories = categoryService.findAll();
        List<Long> itemIds = items.stream().map(Item::getId).collect(Collectors.toList());
        var promotions = promotionService.findByItemIds(itemIds);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", user);
        mav.addObject("items", items);
        mav.addObject("orders", orders);
        mav.addObject("categories", categories);
        mav.addObject("promotions", promotions);
        return mav;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

}
