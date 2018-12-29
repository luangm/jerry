package io.luan.jerry.web;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.dto.PublishCategoryDTO;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.sell.dto.PublishItemDTO;
import io.luan.jerry.sell.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
//
    @PostMapping("/category")
    public ModelAndView publish(@ModelAttribute PublishCategoryDTO request) {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("categorySuccess");
        if (request.getCategoryId() == null) {
            var category = categoryService.publish(request);
            mav.addObject("category", category);
        } else {
            var category = categoryService.edit(request);
            mav.addObject("category", category);
        }
        return mav;
    }

    @GetMapping("/category")
    public ModelAndView category(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        var user = SecurityUtils.getCurrentUser();
        var categories = categoryService.findAll();

        var mav = new ModelAndView("category");

        if (categoryId != null) {
            var existing = categoryService.findById(categoryId);
            if (existing != null) {
                var dto = new PublishCategoryDTO();
                dto.setCategoryId(existing.getId());
                dto.setParentId(existing.getParentId());
                dto.setName(existing.getName());
                dto.setIsLeaf(existing.getIsLeaf());
                mav.addObject("category", dto);
                mav.addObject("categories", categories);
            }
        } else {
            mav.addObject("category", new PublishCategoryDTO());
            mav.addObject("categories", categories);
        }

        return mav;
    }

}
