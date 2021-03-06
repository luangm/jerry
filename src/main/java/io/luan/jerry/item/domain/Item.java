package io.luan.jerry.item.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Item implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * id for the item, for now it's generated by the db
     */
    private Long id;

    /**
     * Seller's userId
     */
    private Long userId;

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
     * Time when the item is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the item is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();

}
