package io.luan.jerry.cart.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemDTO implements Serializable {

    private Boolean selected = false;
    private Long userId;
    private Long itemId;
    private Long quantity;

    public CartItemDTO() {

    }

    public CartItemDTO(Long userId, Long itemId, Long quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
