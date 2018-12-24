package io.luan.jerry.web;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.security.UserPrincipal;
import io.luan.jerry.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuyController {

    private final ItemService itemService;

    private final UserService userService;

    private final OrderService orderService;

    @Autowired
    public BuyController(ItemService itemService, UserService userService, OrderService orderService) {
        this.itemService = itemService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/buy")
    public ModelAndView buy(@RequestParam Long itemId) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        assert auth != null;

        var item = itemService.findById(itemId);
        var principal = (UserPrincipal) auth.getPrincipal();
        var user = principal.getUser();

        ModelAndView modelAndView = new ModelAndView("buy");
        modelAndView.addObject("user", user);
        modelAndView.addObject("item", item);
        return modelAndView;
    }

    @PostMapping("/buy")
    public ModelAndView buyProcess(@ModelAttribute("itemId") Long itemId) {
        var user = SecurityUtils.getCurrentUser();
        var item = itemService.findById(itemId);

        var order = orderService.create(user.getId(), item.getId());

        var mav = new ModelAndView("buySuccess");
        mav.addObject("order", order);
        return mav;
    }
}
