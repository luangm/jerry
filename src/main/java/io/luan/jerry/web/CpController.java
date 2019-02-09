package io.luan.jerry.web;

import io.luan.jerry.category.domain.PropertyOption;
import io.luan.jerry.category.domain.PropertyType;
import io.luan.jerry.category.service.BasePropertyService;
import io.luan.jerry.category.service.BaseValueService;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.category.vm.BasePropertyVM;
import io.luan.jerry.category.vm.CategoryPropertyVM;
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
public class CpController {

    private final CategoryService categoryService;

    private final BasePropertyService propertyService;

    private final BaseValueService valueService;

    @Autowired
    public CpController(CategoryService categoryService, BasePropertyService propertyService, BaseValueService valueService) {
        this.categoryService = categoryService;
        this.propertyService = propertyService;
        this.valueService = valueService;
    }


    @GetMapping("/category/property/edit")
    public ModelAndView categoryPropertyEdit(@RequestParam(value = "categoryId") Long categoryId,
                                             @RequestParam(value = "propertyId") Long propertyId) {
        var cat = categoryService.findById(categoryId, true);
        var cp = cat.getPropertyById(propertyId);

        if (cp != null) {
            var mav = new ModelAndView("categoryProperty");
            mav.addObject("cp", new CategoryPropertyVM(cp));
            var props = propertyService.findAll().stream().map(BasePropertyVM::new).collect(Collectors.toList());
            mav.addObject("properties", props);
            var propTypes = PropertyType.values();
            mav.addObject("propTypes", propTypes);

            var options = PropertyOption.values();
            mav.addObject("propOptions", options);
            return mav;
        }

        var mav = new ModelAndView("redirect:/category/property");
        mav.addObject("categoryId", categoryId);
        return mav;
    }

    @GetMapping("/category/property/new")
    public ModelAndView categoryPropertyEdit(@RequestParam(value = "categoryId") Long categoryId) {
        var cat = categoryService.findById(categoryId, true);
        var cpVM = new CategoryPropertyVM();
        cpVM.setCategoryId(categoryId);
        var mav = new ModelAndView("categoryProperty");
        mav.addObject("cp", cpVM);
        var props = propertyService.findAll().stream().map(BasePropertyVM::new).collect(Collectors.toList());
        mav.addObject("properties", props);

        var propTypes = PropertyType.values();
        mav.addObject("propTypes", propTypes);

        var options = PropertyOption.values();
        mav.addObject("propOptions", options);
        return mav;
    }

    @GetMapping("/category/property")
    public ModelAndView categoryPropertyList(@RequestParam(value = "categoryId") Long categoryId) {
        var cat = categoryService.findById(categoryId, true);
        var props = cat.getProperties().stream().map(CategoryPropertyVM::new).collect(Collectors.toList());

        var mav = new ModelAndView("categoryPropertyList");
        mav.addObject("category", cat);
        mav.addObject("properties", props);

        return mav;
    }

    @PostMapping("/category/property")
    public ModelAndView saveCategoryProperty(@ModelAttribute CategoryPropertyVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var cat = categoryService.findById(vm.getCategoryId(), true);
        var cp = cat.getPropertyById(vm.getPropertyId());
        if (cp != null) {
            // existing
            cp.setAlias(vm.getAlias());
            cp.setSortOrder(vm.getSortOrder());
            cp.setPropertyType(PropertyType.fromValue(vm.getType()));
            cp.setOptions(PropertyOption.fromValues(vm.getOptions()));
            categoryService.save(cat);
        } else {
            // new
            cp = cat.addProperty(vm.getPropertyId(), vm.getAlias());
            cp.setSortOrder(vm.getSortOrder());
            cp.setPropertyType(PropertyType.fromValue(vm.getType()));
            cp.setOptions(PropertyOption.fromValues(vm.getOptions()));
            categoryService.save(cat);
        }
        var mav = new ModelAndView("redirect:/category/property");
        mav.addObject("categoryId", vm.getCategoryId());
        return mav;
    }

}
