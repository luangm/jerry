package io.luan.jerry.item.data;

import io.luan.jerry.item.domain.Item;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class ItemDO {

    private Long id;
    private Long userId;
    private String title;
    private String imgUrl;
    private Long price;
    private Long categoryId;
    private Long inventoryId;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public ItemDO() {
        // do nothing
    }

    public ItemDO(Item item) {
        this.id = item.getId();
        this.userId = item.getUserId();
        this.title = item.getTitle();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
        this.categoryId = item.getCategoryId();
        this.inventoryId = item.getInventoryId();
        this.gmtCreate = item.getGmtCreate();
        this.gmtModified = item.getGmtModified();
    }

}
