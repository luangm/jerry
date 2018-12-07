package io.luan.jerry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("nick")
public class LoginController {


    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginProcess")
    public ModelAndView loginProcess(@RequestParam String nick) {
        var mav = new ModelAndView("success");
        mav.addObject("nick", nick);
        return mav;
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/registerProcess")
    public ModelAndView registerProcess(@RequestParam String nick) {
        userService.register(nick);
        return new ModelAndView("registerSuccess");
    }

}
