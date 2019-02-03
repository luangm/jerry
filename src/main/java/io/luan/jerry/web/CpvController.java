package io.luan.jerry.web;

import io.luan.jerry.category.service.BasePropertyService;
import io.luan.jerry.category.service.BaseValueService;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.category.vm.BaseValueVM;
import io.luan.jerry.category.vm.CategoryPropertyVM;
import io.luan.jerry.category.vm.CategoryPropertyValueVM;
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
public class CpvController {

    private final CategoryService categoryService;

    private final BasePropertyService propertyService;

    private final BaseValueService valueService;

    @Autowired
    public CpvController(CategoryService categoryService, BasePropertyService propertyService, BaseValueService valueService) {
        this.categoryService = categoryService;
        this.propertyService = propertyService;
        this.valueService = valueService;
    }

    @GetMapping("/category/property/value/new")
    public ModelAndView categoryPropertyEdit(@RequestParam(value = "categoryId") Long categoryId,
                                             @RequestParam(value = "propertyId") Long propertyId) {
        var cat = categoryService.findById(categoryId, true);
        var cpvVM = new CategoryPropertyValueVM();
        cpvVM.setCategoryId(categoryId);
        cpvVM.setPropertyId(propertyId);
        var mav = new ModelAndView("cpv");
        mav.addObject("cpv", cpvVM);
//        var props = propertyService.findAll().stream().map(BasePropertyVM::new).collect(Collectors.toList());
//        mav.addObject("properties", props);
        var values = valueService.findAll().stream().map(BaseValueVM::new).collect(Collectors.toList());
        mav.addObject("values", values);
        return mav;
    }

    @GetMapping("/category/property/value/edit")
    public ModelAndView cpvEdit(@RequestParam(value = "categoryId") Long categoryId,
                                @RequestParam(value = "propertyId") Long propertyId,
                                @RequestParam(value = "valueId") Long valueId) {
        var cat = categoryService.findById(categoryId, true);
        var optCp = cat.getProperties().stream().filter(item -> item.getPropertyId().equals(propertyId)).findFirst();
        if (optCp.isPresent()) {
            var cp = optCp.get();
            var optCpv = cp.getValues().stream().filter(item -> item.getValueId().equals(valueId)).findFirst();
            if (optCpv.isPresent()) {
                var cpv = optCpv.get();

                var mav = new ModelAndView("cpv");
                mav.addObject("cpv", new CategoryPropertyValueVM(cpv));
                var values = valueService.findAll().stream().map(BaseValueVM::new).collect(Collectors.toList());
                mav.addObject("values", values);
                return mav;
            }

        }

        var mav = new ModelAndView("redirect:/category/property/value");
        mav.addObject("categoryId", categoryId);
        mav.addObject("propertyId", propertyId);
        return mav;
    }

    @GetMapping("/category/property/value")
    public ModelAndView cpvList(@RequestParam(value = "categoryId") Long categoryId,
                                @RequestParam(value = "propertyId") Long propertyId) {
        var cat = categoryService.findById(categoryId, true);
        var optCp = cat.getProperties().stream().filter(cp -> cp.getPropertyId().equals(propertyId)).findFirst();
        if (optCp.isPresent()) {
            var cp = optCp.get();
            var values = cp.getValues().stream().map(CategoryPropertyValueVM::new).collect(Collectors.toList());
            var mav = new ModelAndView("cpvList");
            mav.addObject("category", cat);
            mav.addObject("cp", new CategoryPropertyVM(cp));
            mav.addObject("values", values);
            return mav;
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("/category/property/value")
    public ModelAndView saveCategoryProperty(@ModelAttribute CategoryPropertyValueVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var cat = categoryService.findById(vm.getCategoryId(), true);
        var optCp = cat.getProperties().stream().filter(item -> item.getPropertyId().equals(vm.getPropertyId())).findFirst();
        if (optCp.isPresent()) {
            var cp = optCp.get();
            var optCpv = cp.getValues().stream().filter(cpv -> cpv.getValueId().equals(vm.getValueId())).findFirst();
            if (optCpv.isPresent()) {
                // existing
                var cpv = optCpv.get();
                cpv.setAlias(vm.getAlias());
                cpv.setSortOrder(vm.getSortOrder());
                categoryService.save(cat);
            } else {
                var cpv = cp.addValue(vm.getValueId(), vm.getAlias());
                cpv.setSortOrder(vm.getSortOrder());
                categoryService.save(cat);
            }

            var mav = new ModelAndView("redirect:/category/property/value");
            mav.addObject("categoryId", vm.getCategoryId());
            mav.addObject("propertyId", vm.getPropertyId());
            return mav;
        }

        throw new IllegalArgumentException();
    }

}
