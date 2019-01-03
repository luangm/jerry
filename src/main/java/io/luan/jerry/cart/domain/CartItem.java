package io.luan.jerry.cart.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.common.domain.EntityState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class CartItem extends Entity {

    /**
     * CartItem ID
     */
    private Long id;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Item ID
     */
    private Long itemId;

    /**
     * Quantity of Item
     */
    private Integer quantity;

    /**
     * Time when the item is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the item is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();

    /**
     * Status
     */
    private CartItemState status = CartItemState.Normal;

    public CartItem() {
        this.setState(EntityState.Added);
    }

    public void setQuantity(Integer newValue) {
        if (!newValue.equals(quantity)) {
            firePropertyChange("quantity", quantity, newValue);
            this.quantity = newValue;
            this.gmtModified = LocalDateTime.now();
        }
    }

    public void setStatus(CartItemState newState) {
        if (newState != this.status) {
            firePropertyChange("status", status, newState);
            this.status = newState;
            this.gmtModified = LocalDateTime.now();
        }
    }
}
