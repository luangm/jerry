package io.luan.jerry.web;

import io.luan.jerry.buy.dto.CreateOrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuyController {

    private final ItemService itemService;

    private final BuyService buyService;

    @Autowired
    public BuyController(ItemService itemService, BuyService buyService) {
        this.itemService = itemService;
        this.buyService = buyService;
    }

    @GetMapping("/buy")
    public ModelAndView buy(@RequestParam Long itemId) {

        var user = SecurityUtils.getCurrentUser();
        var item = itemService.findById(itemId);

        ModelAndView modelAndView = new ModelAndView("buy");
        modelAndView.addObject("user", user);
        modelAndView.addObject("item", item);
        modelAndView.addObject("request", new CreateOrderDTO());

        return modelAndView;
    }

    @PostMapping("/buy")
    public ModelAndView buyProcess(@ModelAttribute @Validated CreateOrderDTO request) {
        var user = SecurityUtils.getCurrentUser();
        request.setUserId(user.getId());

        var order = buyService.createOrder(request);

        var mav = new ModelAndView("buySuccess");
        mav.addObject("order", order);
        return mav;
    }
}
