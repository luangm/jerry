package io.luan.jerry.sell.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PublishItemRequest implements Serializable {

    static final long serialVersionUID = 1L;

    private Long itemId;
    private Long categoryId;
    private String title;
    private String imgUrl;
    private Long price;
    private Long inventory;
    private List<PublishItemSku> skuList = new ArrayList<>();

}
