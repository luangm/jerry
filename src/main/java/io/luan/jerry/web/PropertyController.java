package io.luan.jerry.web;

import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.service.BasePropertyService;
import io.luan.jerry.category.vm.BasePropertyVM;
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
public class PropertyController {

    private final BasePropertyService propertyService;

    @Autowired
    public PropertyController(BasePropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/property/edit")
    public ModelAndView propertyEdit(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        var prop = propertyService.findById(id);
        var mav = new ModelAndView("property");
        mav.addObject("property", new BasePropertyVM(prop));
        return mav;
    }

    @GetMapping("/property")
    public ModelAndView propertyList() {
        var user = SecurityUtils.getCurrentUser();
        var properties = propertyService.findAll();
        var propVMs = properties.stream().map(BasePropertyVM::new).collect(Collectors.toList());

        var mav = new ModelAndView("propertyList");
        mav.addObject("properties", propVMs);
        return mav;
    }

    @GetMapping("/property/new")
    public ModelAndView propertyNew() {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("property");
        mav.addObject("property", new BasePropertyVM());
        return mav;
    }

    @PostMapping("/property")
    public ModelAndView save(@ModelAttribute BasePropertyVM request) {
        var user = SecurityUtils.getCurrentUser();

        BaseProperty prop;
        if (request.getId() == null) {
            prop = new BaseProperty();
            prop.setName(request.getName());
        } else {
            prop = propertyService.findById(request.getId());
            prop.setName(request.getName());
        }

        propertyService.save(prop);
        var mav = new ModelAndView("redirect:/property");
        return mav;
    }

    @GetMapping("/property/disable")
    public ModelAndView propertyDisable(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();

        propertyService.disableById(id);
        var mav = new ModelAndView("redirect:/property");
        return mav;
    }

    @GetMapping("/property/enable")
    public ModelAndView propertyEnable(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        propertyService.enableById(id);
        var mav = new ModelAndView("redirect:/property");
        return mav;
    }

}
