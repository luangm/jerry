package io.luan.jerry.cart.data;

import io.luan.jerry.cart.domain.CartItem;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CartItemDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Long itemId;
    private Integer quantity;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Integer status;

    public CartItemDO() {
        //
    }

    public CartItemDO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.itemId = cartItem.getItemId();
        this.userId = cartItem.getUserId();
        this.quantity = cartItem.getQuantity();
        this.gmtCreate = cartItem.getGmtCreate();
        this.gmtModified = cartItem.getGmtModified();
        this.status = cartItem.getStatus().getValue();
    }

}
