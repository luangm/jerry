package io.luan.jerry.item.data;

import io.luan.jerry.item.domain.Item;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDO {

    private Long id;
    private Long userId;
    private String title;
    private String imgUrl;
    private Long price;
    private Long categoryId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public static ItemDO fromEntity(Item item) {
        var itemDO = new ItemDO();
        itemDO.setId(item.getId());
        itemDO.setUserId(item.getUserId());
        itemDO.setTitle(item.getTitle());
        itemDO.setImgUrl(item.getImgUrl());
        itemDO.setPrice(item.getPrice());
        itemDO.setCategoryId(item.getCategoryId());
        itemDO.setGmtCreate(item.getGmtCreate());
        itemDO.setGmtModified(item.getGmtModified());
        return itemDO;
    }

    public Item toEntity() {
        var item = new Item();
        item.setId(this.getId());
        item.setUserId(this.getUserId());
        item.setTitle(this.getTitle());
        item.setImgUrl(this.getImgUrl());
        item.setPrice(this.getPrice());
        item.setCategoryId(this.getCategoryId());
        item.setGmtCreate(this.getGmtCreate());
        item.setGmtModified(this.getGmtModified());
        return item;
    }
}
