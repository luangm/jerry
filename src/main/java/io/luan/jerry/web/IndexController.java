package io.luan.jerry.web;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Security;

@Controller
public class IndexController {

    private final ItemService itemService;

    private final OrderService orderService;

    @Autowired
    public IndexController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }


    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        var user = SecurityUtils.getCurrentUser();

        var items = itemService.findAll();
        var orders = orderService.findAll();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", user);
        modelAndView.addObject("items", items);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

}
