package io.luan.jerry.cart.vm;

import io.luan.jerry.cart.domain.CartItem;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemVM implements Serializable {

    private Boolean selected = false;
    private Long cartItemId;
    private Long itemId;
    private Long skuId;
    private Long userId;
    private Long quantity;

    public CartItemVM() {
        // Required by framework
    }

    public CartItemVM(CartItem entity) {
        this.cartItemId = entity.getId();
        this.itemId = entity.getItemId();
        this.skuId = entity.getSkuId();
        this.userId = entity.getUserId();
        this.quantity = entity.getQuantity();
    }
}
