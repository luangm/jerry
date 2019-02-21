package io.luan.jerry.detail.dto;


import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.item.domain.Sku;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItemSku implements Serializable {

    private Long id;
    private String imgUrl;
    private Long price;
    private String pvMap;
    private Long inventory;
    private Long inventoryId;

    public ItemSku(Sku sku) {
        this.id = sku.getId();
        this.imgUrl = sku.getImgUrl();
        this.price = sku.getPrice();
        this.inventoryId = sku.getInventoryId();
        this.pvMap = MapUtils.encode(sku.getPropertyMap());
    }

}
