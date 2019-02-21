package io.luan.jerry.sell.vm;

import io.luan.jerry.item.domain.Item;
import io.luan.jerry.sell.dto.PublishItemRequest;
import io.luan.jerry.sell.dto.PublishItemSku;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PublishItemVM implements Serializable {

    static final long serialVersionUID = 1L;

    private Long itemId;
    private Long categoryId;
    private String title;
    private String imgUrl;
    private Long price;
    private Long inventory;
    private Map<String, List<String>> props = new HashMap<>();
    private Map<String, Long> skuPrice = new HashMap<>();
    private Map<String, Long> skuInventory = new HashMap<>();

    public PublishItemVM() {
        //
    }

    public PublishItemVM(Item item) {
        this.itemId = item.getId();
        this.categoryId = item.getCategoryId();
        this.title = item.getTitle();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
    }

    public PublishItemRequest toDTO() {
        var dto = new PublishItemRequest();
        dto.setItemId(this.itemId);
        dto.setCategoryId(this.categoryId);
        dto.setTitle(this.title);
        dto.setImgUrl(this.imgUrl);
        dto.setPrice(this.price);
        dto.setInventory(this.inventory);

        List<PublishItemSku> list = new ArrayList<>();
        for(var key: skuPrice.keySet()) {
            var sku = new PublishItemSku();
            sku.setPv(key);
            sku.setPrice(skuPrice.get(key));
            sku.setInventory(skuInventory.get(key));
            list.add(sku);
        }
        dto.setSkuList(list);

        return dto;
    }
}
