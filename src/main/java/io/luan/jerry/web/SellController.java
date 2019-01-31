package io.luan.jerry.web;

import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.inventory.service.InventoryService;
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
public class SellController {

    private final SellService sellService;

    private final ItemService itemService;

    private final CategoryService categoryService;

    private final InventoryService inventoryService;

    @Autowired
    public SellController(SellService sellService, ItemService itemService, CategoryService categoryService, InventoryService inventoryService) {
        this.sellService = sellService;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
    }

    @PostMapping("/sell")
    public ModelAndView publish(@ModelAttribute PublishItemDTO request) {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("sellSuccess");
        if (request.getItemId() == null) {
            var item = sellService.publish(user, request);
            mav.addObject("item", item);
        } else {
            var item = sellService.edit(user, request);
            mav.addObject("item", item);
        }
        return mav;
    }

    @GetMapping("/sell")
    public ModelAndView sell(@RequestParam(value = "itemId", required = false) Long itemId) {
        var user = SecurityUtils.getCurrentUser();
        var categories = categoryService.findAll();

        var mav = new ModelAndView("sell");

        if (itemId != null) {
            var existing = itemService.findById(itemId);
            if (existing != null && existing.getUserId() == user.getId()) {

                var inventory = inventoryService.findById(existing.getInventoryId());

                var dto = new PublishItemDTO();
                dto.setCategoryId(existing.getCategoryId());
                dto.setTitle(existing.getTitle());
                dto.setImgUrl(existing.getImgUrl());
                dto.setPrice(existing.getPrice());
                dto.setItemId(itemId);
                dto.setInventory(inventory.getAvailable());
                mav.addObject("item", dto);
                mav.addObject("categories", categories);
            }
        } else {
            mav.addObject("item", new PublishItemDTO());
            mav.addObject("categories", categories);
        }

        return mav;
    }

}
