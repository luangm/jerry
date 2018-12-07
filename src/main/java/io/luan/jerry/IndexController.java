package io.luan.jerry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final ItemService itemService;

    @Autowired
    public IndexController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/")
    public ModelAndView index(@SessionAttribute(value = "nick", required = false) String nick) {
        var items = itemService.getAll();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("nick", nick);
        modelAndView.addObject("items", items);
        return modelAndView;
    }

}
