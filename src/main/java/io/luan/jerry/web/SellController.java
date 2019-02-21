package io.luan.jerry.web;

import io.luan.jerry.category.domain.PropertyType;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.security.SecurityUtils;
import io.luan.jerry.sell.dto.PublishItemRequest;
import io.luan.jerry.sell.service.SellService;
import io.luan.jerry.sell.vm.PublishItemVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ModelAndView publish(@ModelAttribute PublishItemVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("sellSuccess");
        var dto = vm.toDTO();

        if (vm.getItemId() == null) {
            var item = sellService.publish(user, dto);
            mav.addObject("item", item);
        } else {
            var item = sellService.edit(user, dto);
            mav.addObject("item", item);
        }
        return mav;
    }

    @GetMapping("/sell")
    public ModelAndView sell(@RequestParam(value = "itemId", required = false) Long itemId,
                             @RequestParam(value = "categoryId", required = false) Long categoryId) {
        var user = SecurityUtils.getCurrentUser();

        if (itemId == null && categoryId == null) {
            var mav = new ModelAndView("selectCategory");
            var categories = categoryService.findAll();
            mav.addObject("categoryId", null);
            mav.addObject("categories", categories);
            return mav;
        }

        if (itemId != null) {
            return sellExisting(itemId, user.getId());
        } else {
            return sellNew(categoryId);
        }
    }

    private ModelAndView sellExisting(Long itemId, Long userId) {
        var mav = new ModelAndView("sell");
        var existing = itemService.findById(itemId, true);
        if (existing != null && existing.getUserId().equals(userId)) {
            var category = categoryService.findById(existing.getCategoryId(), true);
            var inventory = inventoryService.findById(existing.getInventoryId());
            var inventories = inventoryService.findAllByItemId(itemId);

            var vm = new PublishItemVM();
            vm.setCategoryId(existing.getCategoryId());
            vm.setTitle(existing.getTitle());
            vm.setImgUrl(existing.getImgUrl());
            vm.setPrice(existing.getPrice());
            vm.setItemId(itemId);
            if (inventory != null) {
                vm.setInventory(inventory.getAvailable());
            }

            Map<String, List<String>> propMap = new HashMap<>();
            Map<String, Long> skuPrices = new HashMap<>();
            Map<String, Long> skuInventories = new HashMap<>();

            for (var sku: existing.getSkus()) {
                var pv = MapUtils.encode(sku.getPropertyMap());
                skuPrices.put(pv, sku.getPrice());

                for (var item : inventories) {
                    if (item.getId().equals(sku.getInventoryId())) {
                        skuInventories.put(pv, item.getAvailable());
                        break;
                    }
                }

                for (var kv : sku.getPropertyMap().entrySet()) {
                    var key = kv.getKey().toString();
                    var val = kv.getValue().toString();

                    List<String> list = propMap.get(key);
                    if (list == null) {
                        list = new ArrayList<>();
                        propMap.put(key, list);
                    }

                    if (!list.contains(val)) {
                        list.add(val);
                    }
                }
            }
            vm.setProps(propMap);
            vm.setSkuPrice(skuPrices);
            vm.setSkuInventory(skuInventories);

            mav.addObject("item", vm);
            mav.addObject("category", category);

            var saleProps = category.getProperties().stream().filter(item -> item.getPropertyType() == PropertyType.Sale).collect(Collectors.toList());
            if (saleProps.size() > 0) {
                mav.addObject("saleProps", saleProps);
            }
        }


        return mav;
    }

    private ModelAndView sellNew(Long categoryId) {
        var category = categoryService.findById(categoryId, true);
        var mav = new ModelAndView("sell");
        mav.addObject("item", new PublishItemVM());
        mav.addObject("category", category);

        var saleProps = category.getProperties().stream().filter(item -> item.getPropertyType() == PropertyType.Sale).collect(Collectors.toList());

        if (saleProps.size() > 0) {
            mav.addObject("saleProps", saleProps);
        }

        return mav;
    }

}
