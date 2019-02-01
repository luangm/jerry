package io.luan.jerry.detail.service.impl;

import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.detail.dto.DetailDTO;
import io.luan.jerry.detail.service.DetailService;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.promotion.service.PromotionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class DetailServiceImpl implements DetailService {

    private final ItemService itemService;

    private final InventoryService inventoryService;

    private final CategoryService categoryService;

    private final PromotionService promotionService;

    @Autowired
    public DetailServiceImpl(ItemService itemService, InventoryService inventoryService, CategoryService categoryService, PromotionService promotionService) {
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.categoryService = categoryService;
        this.promotionService = promotionService;
    }

    @Override
    public DetailDTO getDetail(Long itemId) {
        var item = itemService.findById(itemId);
        var inventory = inventoryService.findById(item.getInventoryId());
        var category = categoryService.findById(item.getCategoryId());
        var promotions = promotionService.findByItemId(itemId);

        var detailDto = new DetailDTO();
        detailDto.setItemId(itemId);
        detailDto.setSellerId(item.getUserId());
        detailDto.setTitle(item.getTitle());
        detailDto.setImgUrl(item.getImgUrl());
        detailDto.setPrice(item.getPrice());

        if (promotions.size() > 0) {
            var promo = promotions.get(0);
            detailDto.setNewPrice(promo.getNewPrice());
        }

        if (inventory != null) {
            detailDto.setInventory(inventory.getAvailable());
        }

        return detailDto;
    }
}
