package io.luan.jerry.web;

import io.luan.jerry.user.dto.UserLoginDTO;
import io.luan.jerry.user.dto.UserRegistrationDTO;
import io.luan.jerry.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

//    @PostMapping(value = "/login")
//    public ModelAndView loginProcess(@ModelAttribute UserLoginDTO dto, HttpSession session) {
//        var user = userService.findByUsername(dto.getUsername());
//        var success = false;
//        if (user != null) {
//            success = user.verifyPassword(dto.getPassword());
//        }
//
//        if (success) {
//            session.setAttribute("user", user);
//            var mav = new ModelAndView("success");
//            mav.addObject("user", user);
//            return mav;
//        }
//
//        return new ModelAndView("fail");
//    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        return "logout";
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        var mav = new ModelAndView("register");
        mav.addObject("register", new UserRegistrationDTO());
        return mav;
    }

    @PostMapping(value = "/register")
    public ModelAndView registerProcess(@ModelAttribute UserRegistrationDTO dto) {
        var user = userService.register(dto);
        return new ModelAndView("registerSuccess");
    }

}
