package io.luan.jerry.sell.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishItemDTO implements Serializable {

    private Long itemId;
    private String title;
    private String imgUrl;
    private Long price;

}
