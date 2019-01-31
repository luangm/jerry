package io.luan.jerry.sell.service.impl;

import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.sell.dto.PublishItemDTO;
import io.luan.jerry.sell.service.SellService;
import io.luan.jerry.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class SellServiceImpl implements SellService {

    private final ItemService itemService;

    private final InventoryService inventoryService;

    @Autowired
    public SellServiceImpl(ItemService itemService, InventoryService inventoryService) {
        this.itemService = itemService;
        this.inventoryService = inventoryService;
    }

    @Override
    public Item publish(User user, PublishItemDTO request) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        var item = new Item();
        item.setUserId(user.getId());
        item.setCategoryId(request.getCategoryId());
        item.setTitle(request.getTitle());
        item.setImgUrl(request.getImgUrl());
        item.setPrice(request.getPrice());

        itemService.save(item);

        var inventory = new Inventory();
        inventory.setItemId(item.getId());
        inventory.setAvailable(request.getInventory());

        inventoryService.save(inventory);

        item.setInventoryId(inventory.getId());
        itemService.save(item);

        return item;
    }

    @Override
    public Item edit(User user, PublishItemDTO request) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        var item = itemService.findById(request.getItemId());
        if (item == null) {
            throw new IllegalArgumentException("item not found");
        }
        if (user.getId() != item.getUserId()) {
            throw new RuntimeException("Cannot modify other people's item");
        }

        var inventory = inventoryService.findById(item.getInventoryId());
        inventory.setAvailable(request.getInventory());
        inventoryService.save(inventory);

        item.setTitle(request.getTitle());
        item.setImgUrl(request.getImgUrl());
        item.setPrice(request.getPrice());
        item.setGmtModified(OffsetDateTime.now().withNano(0));

        return itemService.save(item);
    }
}
