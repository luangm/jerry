package io.luan.jerry.web;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
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

    @RequestMapping("/buy")
    public ModelAndView buy(@SessionAttribute(value = "nick", required = false) String nick, @RequestParam("itemId") Long itemId) {

        var item = itemService.findById(itemId);
        var user = userService.findByNick(nick);

        ModelAndView modelAndView = new ModelAndView("buy");
        modelAndView.addObject("user", user);
        modelAndView.addObject("item", item);
        return modelAndView;
    }

    @RequestMapping(value = "/buyProcess")
    public ModelAndView loginProcess(@SessionAttribute("nick") String nick, @RequestParam Long itemId) {
        var item = itemService.findById(itemId);
        var user = userService.findByNick(nick);

        var order = orderService.create(user.getId(), itemId);

        var mav = new ModelAndView("buySuccess");
        mav.addObject("order", order);
        return mav;
    }
}
