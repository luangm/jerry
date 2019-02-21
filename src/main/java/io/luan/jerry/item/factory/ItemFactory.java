package io.luan.jerry.item.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.item.data.ItemDO;
import io.luan.jerry.item.data.SkuDO;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.domain.Sku;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemFactory {

    public Item load(ItemDO itemDO) {
        var item = new Item();
        item.setId(itemDO.getId());
        item.setUserId(itemDO.getUserId());
        item.setTitle(itemDO.getTitle());
        item.setImgUrl(itemDO.getImgUrl());
        item.setPrice(itemDO.getPrice());
        item.setCategoryId(itemDO.getCategoryId());
        item.setGmtCreate(itemDO.getGmtCreate());
        item.setInventoryId(itemDO.getInventoryId());

        // Note: GmtModified and State should ALWAYS be set last in that order
        item.setGmtModified(itemDO.getGmtModified());
        item.setState(EntityState.Unchanged);
        return item;
    }

    public Item load(ItemDO itemDO, List<SkuDO> skuDoList) {
        var item = load(itemDO);

        List<Sku> skuList = new ArrayList<>();
        for (var skuDO : skuDoList) {
            var sku = load(skuDO);
            sku.setItem(item);
            skuList.add(sku);
        }
        item.setSkus(skuList);

        // Note: GmtModified and State should ALWAYS be set last in that order
        item.setGmtModified(itemDO.getGmtModified());
        item.setState(EntityState.Unchanged);
        return item;
    }

    public Sku load(SkuDO skuDO) {
        var sku = new Sku();
        sku.setId(skuDO.getId());
//        sku.setItemId(skuDO.getItemId());
        sku.setImgUrl(skuDO.getImgUrl());
        sku.setPrice(skuDO.getPrice());
        sku.setGmtCreate(skuDO.getGmtCreate());
        sku.setInventoryId(skuDO.getInventoryId());
        sku.setPropertyMap(MapUtils.decodeLongLong(skuDO.getProperties()));

        // Note: GmtModified and State should ALWAYS be set last in that order
        sku.setGmtModified(skuDO.getGmtModified());
        sku.setState(EntityState.Unchanged);
        return sku;
    }
}
