package io.luan.jerry.web;

import io.luan.jerry.detail.service.DetailService;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DetailController {

    private final DetailService detailService;

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("itemId") Long itemId) {
        var user = SecurityUtils.getCurrentUser();

        var detailDto = detailService.getDetail(itemId);

        var mav = new ModelAndView("detail");
        mav.addObject("detail", detailDto);
        mav.addObject("loggedIn", user != null);
        return mav;
    }

}
