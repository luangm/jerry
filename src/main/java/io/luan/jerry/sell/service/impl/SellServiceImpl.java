package io.luan.jerry.sell.service.impl;

import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.domain.Sku;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.sell.dto.PublishItemRequest;
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
    public Item publish(User user, PublishItemRequest request) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        var item = new Item();
        item.setUserId(user.getId());
        item.setCategoryId(request.getCategoryId());
        item.setTitle(request.getTitle());
        item.setImgUrl(request.getImgUrl());
        item.setPrice(request.getPrice());

        boolean haveSku = false;

        for (var publishSku : request.getSkuList()) {
            haveSku = true;

            var pvMap = MapUtils.decodeLongLong(publishSku.getPv());
            var sku = new Sku();
            sku.setPrice(publishSku.getPrice());
            for (var set : pvMap.entrySet()) {
                sku.setPropertyValue(set.getKey(), set.getValue());
            }
            item.addSku(sku);
        }
        itemService.save(item);

        if (!haveSku) {
            // No SKU
            var inventory = new Inventory();
            inventory.setItemId(item.getId());
            inventory.setAvailable(request.getInventory());
            inventoryService.save(inventory);

            item.setInventoryId(inventory.getId());
            itemService.save(item);
        } else {
            // Has SKU, only store Sku Inventory
            for (var sku : item.getSkus()) {
                var skuInventory = new Inventory();
                skuInventory.setItemId(item.getId());
                skuInventory.setSkuId(sku.getId());

                for (var publishSku : request.getSkuList()) {
                    var pvMap = MapUtils.decodeLongLong(publishSku.getPv());
                    if (MapUtils.Equals(pvMap, sku.getPropertyMap())) {
                        skuInventory.setAvailable(publishSku.getInventory());
                        break;
                    }
                }

                inventoryService.save(skuInventory);

                sku.setInventoryId(skuInventory.getId());
            }

            itemService.save(item);
        }

        return item;
    }

    @Override
    public Item edit(User user, PublishItemRequest request) {
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
