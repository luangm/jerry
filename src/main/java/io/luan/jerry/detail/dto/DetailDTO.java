package io.luan.jerry.detail.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetailDTO implements Serializable {

    /**
     * Item ID
     */
    private Long itemId;

    /**
     * Seller's ID
     */
    private Long sellerId;

    /**
     * Title of the item
     */
    private String title;

    /**
     * Image of the item
     */
    private String imgUrl;

    /**
     * Price in cents
     */
    private Long price;

    /**
     * Leaf category ID
     */
    private Long categoryId;

    /**
     * Inventory
     */
    private Long inventory = 0L;

    /**
     * After Promotion
     */
    private Long newPrice;
}
