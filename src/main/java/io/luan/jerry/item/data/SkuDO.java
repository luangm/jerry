package io.luan.jerry.item.data;

import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.item.domain.Sku;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SkuDO {

    private Long id;
    private Long itemId;
    private String imgUrl;
    private Long price;
    private Long inventoryId;
    private String properties;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public SkuDO() {
        // do nothing
    }

    public SkuDO(Sku sku) {
        var item = sku.getItem();
        this.id = sku.getId();
        this.itemId = item.getId();
        this.imgUrl = sku.getImgUrl();
        this.price = sku.getPrice();
        this.inventoryId = sku.getInventoryId();
        this.gmtCreate = sku.getGmtCreate();
        this.gmtModified = sku.getGmtModified();
        this.properties = MapUtils.encode(sku.getPropertyMap());
    }

}
