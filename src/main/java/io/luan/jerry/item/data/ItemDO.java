package io.luan.jerry.item.data;

import io.luan.jerry.item.domain.Item;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDO {

    private Long id;
    private String title;
    private Long price;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public static ItemDO fromEntity(Item item) {
        var itemDO = new ItemDO();
        itemDO.setId(item.getId());
        itemDO.setTitle(item.getTitle());
        itemDO.setPrice(item.getPrice());
        itemDO.setGmtCreate(item.getGmtCreate());
        itemDO.setGmtModified(item.getGmtModified());
        return itemDO;
    }

    public Item toEntity() {
        var item = new Item();
        item.setId(this.getId());
        item.setTitle(this.getTitle());
        item.setPrice(this.getPrice());
        item.setGmtCreate(this.getGmtCreate());
        item.setGmtModified(this.getGmtModified());
        return item;
    }
}
