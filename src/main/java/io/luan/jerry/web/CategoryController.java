package io.luan.jerry.web;

import io.luan.jerry.category.dto.PublishCategoryDTO;
import io.luan.jerry.category.service.BasePropertyService;
import io.luan.jerry.category.service.BaseValueService;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.category.vm.CategoryVM;
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
public class CategoryController {

    private final CategoryService categoryService;

    private final BasePropertyService propertyService;

    private final BaseValueService valueService;

    @Autowired
    public CategoryController(CategoryService categoryService, BasePropertyService propertyService, BaseValueService valueService) {
        this.categoryService = categoryService;
        this.propertyService = propertyService;
        this.valueService = valueService;
    }

    @GetMapping("/category/new")
    public ModelAndView category(@RequestParam(value = "parentId", required = false) Long parentId) {
        var user = SecurityUtils.getCurrentUser();
        var categories = categoryService.findAll();
        var catVMs = categories.stream().map(CategoryVM::new).collect(Collectors.toList());

        var vm = new CategoryVM();
        vm.setParentId(parentId != null ? parentId : Long.valueOf(0L));

        var mav = new ModelAndView("category");
        mav.addObject("category", vm);
        mav.addObject("categories", catVMs);

        return mav;
    }

    @GetMapping("/category/disable")
    public ModelAndView categoryDisable(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();

        categoryService.disableById(id);
        var mav = new ModelAndView("redirect:/category");
        return mav;
    }

    @GetMapping("/category/edit")
    public ModelAndView categoryEdit(@RequestParam("id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        var categories = categoryService.findAll();
        var catVMs = categories.stream().map(CategoryVM::new).collect(Collectors.toList());

        var mav = new ModelAndView("category");

        var existing = categoryService.findById(id);
        mav.addObject("category", new CategoryVM(existing));
        mav.addObject("categories", catVMs);
        return mav;
    }

    @GetMapping("/category/enable")
    public ModelAndView categoryEnable(@RequestParam(value = "id") Long id) {
        var user = SecurityUtils.getCurrentUser();
        categoryService.enableById(id);
        var mav = new ModelAndView("redirect:/category");
        return mav;
    }

    @GetMapping("/category")
    public ModelAndView categoryList() {
        var user = SecurityUtils.getCurrentUser();
        var categories = categoryService.findAll();
        var catVMs = categories.stream().map(CategoryVM::new).collect(Collectors.toList());
        var mav = new ModelAndView("categoryList");
        mav.addObject("categories", catVMs);
        return mav;
    }


    @PostMapping("/category")
    public ModelAndView publish(@ModelAttribute CategoryVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("redirect:/category");
        if (vm.getId() == null) {
            var request = new PublishCategoryDTO();
            request.setParentId(vm.getParentId());
            request.setName(vm.getName());
            categoryService.publish(request);
        } else {
            var request = new PublishCategoryDTO();
            request.setCategoryId(vm.getId());
            request.setParentId(vm.getParentId());
            request.setName(vm.getName());
            request.setIsLeaf(vm.getIsLeaf());
            categoryService.edit(request);
        }
        return mav;
    }

}
