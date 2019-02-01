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
    private Long quantity;

    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

    /**
     * Status
     */
    private CartItemState status = CartItemState.Normal;

    public CartItem() {
        this.setState(EntityState.Added);
    }

    public void setQuantity(Long newValue) {
        if (!newValue.equals(quantity)) {
            firePropertyChange("quantity", quantity, newValue);
            this.quantity = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }

    public void setStatus(CartItemState newState) {
        if (newState != this.status) {
            firePropertyChange("status", status, newState);
            this.status = newState;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }
}
