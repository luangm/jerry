package io.luan.jerry.sell.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishItemDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long itemId;
    private Long categoryId;
    private String title;
    private String imgUrl;
    private Long price;
    private Integer inventory;

}
