package io.luan.jerry.item.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.item.data.ItemDO;
import io.luan.jerry.item.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemFactory {

    public Item loadFromDataObject(ItemDO itemDO) {
        var item = new Item();
        item.setId(itemDO.getId());
        item.setUserId(itemDO.getUserId());
        item.setTitle(itemDO.getTitle());
        item.setImgUrl(itemDO.getImgUrl());
        item.setPrice(itemDO.getPrice());
        item.setCategoryId(itemDO.getCategoryId());
        item.setGmtCreate(itemDO.getGmtCreate());
        item.setGmtModified(itemDO.getGmtModified());
        item.setState(EntityState.Unchanged);
        return item;
    }
}
