package io.luan.jerry.web;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index(@SessionAttribute(value = "nick", required = false) String nick) {
        var items = itemService.findAll();
        var orders = orderService.findAll();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("nick", nick);
        modelAndView.addObject("items", items);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

}
