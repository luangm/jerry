package io.luan.jerry.web;

import io.luan.jerry.category.domain.BaseValue;
import io.luan.jerry.category.service.BaseValueService;
import io.luan.jerry.category.vm.BaseValueVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
public class ValueController {

    @Autowired
    private BaseValueService valueService;

    @PostMapping("/value")
    public ModelAndView save(@ModelAttribute BaseValueVM request) {
        var user = SecurityUtils.getCurrentUser();

        BaseValue value;
        if (request.getId() == null) {
            value = new BaseValue();
            value.setValue(request.getValue());
            valueService.save(value);
        } else {
            throw new IllegalArgumentException();
        }

        var mav = new ModelAndView("redirect:/value");
        return mav;
    }

    @GetMapping("/value/disable")
    public ModelAndView valueDisable(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        valueService.disableById(id);
        var mav = new ModelAndView("redirect:/value");
        return mav;
    }

    @GetMapping("/value/enable")
    public ModelAndView valueEnable(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        valueService.enableById(id);
        var mav = new ModelAndView("redirect:/value");
        return mav;
    }

    @GetMapping("/value/edit")
    public ModelAndView valueEdit(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        var value = valueService.findById(id);
        var mav = new ModelAndView("value");
        mav.addObject("value", new BaseValueVM(value));
        return mav;
    }

    @GetMapping("/value")
    public ModelAndView valueList() {
        var user = SecurityUtils.getCurrentUser();
        var values = valueService.findAll();
        var vmList = values.stream().map(BaseValueVM::new).collect(Collectors.toList());
        var mav = new ModelAndView("valueList");
        mav.addObject("values", vmList);
        return mav;
    }

    @GetMapping("/value/new")
    public ModelAndView valueNew() {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("value");
        mav.addObject("value", new BaseValueVM());
        return mav;
    }

}
