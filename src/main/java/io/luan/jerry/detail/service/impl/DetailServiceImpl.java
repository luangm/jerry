package io.luan.jerry.detail.service.impl;

import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.detail.dto.*;
import io.luan.jerry.detail.service.DetailService;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.promotion.service.PromotionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public ItemDetail getDetail(Long itemId, Long userId) {
        var item = itemService.findById(itemId, true);
//        var inventory = inventoryService.findById(item.getInventoryId());
        var allInventory = inventoryService.findAllByItemId(item.getId());
        var category = categoryService.findById(item.getCategoryId(), true);
        var promotions = promotionService.findByItemId(itemId);

        var detailDto = new ItemDetail(item);
        detailDto.setProperties(category.getProperties().stream().map(SaleProperty::new).collect(Collectors.toList()));

        var total = 0L;
        for (var sku: detailDto.getSkuList()) {
            var skuInventory = allInventory.stream().filter(i->i.getId().equals(sku.getInventoryId())).findFirst();
            if (skuInventory.isPresent()) {
                sku.setInventory(skuInventory.get().getAvailable());
                total += skuInventory.get().getAvailable();
            }
        }

        if (detailDto.getSkuList().size() > 0) {
            detailDto.setInventory(total);
        } else {
            var itemInventory = allInventory.stream().filter(i -> i.getId().equals(item.getInventoryId())).findFirst();
            itemInventory.ifPresent(inventory -> detailDto.setInventory(inventory.getAvailable()));
        }

        return detailDto;
    }
}
