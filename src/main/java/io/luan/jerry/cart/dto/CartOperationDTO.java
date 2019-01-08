package io.luan.jerry.cart.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartOperationDTO implements Serializable {

    private String action;

    private List<CartItemDTO> cartItems = new ArrayList<>();

    private Long itemId;

    private Integer quantity;

    /**
     * Source: can be index or cart
     */
    private String source;



}
