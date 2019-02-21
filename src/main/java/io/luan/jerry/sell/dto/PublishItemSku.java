package io.luan.jerry.sell.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishItemSku implements Serializable {

    private String pv;
    private Long price;
    private Long inventory;
}
