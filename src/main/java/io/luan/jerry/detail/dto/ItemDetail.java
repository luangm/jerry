package io.luan.jerry.detail.dto;

import io.luan.jerry.item.domain.Item;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemDetail implements Serializable {

    private Long id;
    private Long sellerId;
    private String title;
    private String imgUrl;
    private Long price;
    private Long inventory = 0L;
    private List<SaleProperty> properties = new ArrayList<>();
    private List<ItemSku> skuList;

    public ItemDetail(Item item) {
        this.id = item.getId();
        this.sellerId = item.getUserId();
        this.title = item.getTitle();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
        this.skuList = item.getSkus().stream().map(ItemSku::new).collect(Collectors.toList());
    }

}
