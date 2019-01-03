package io.luan.jerry.cart.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.common.domain.EntityState;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Cart extends Entity {

    private static final long serialVersionUID = 1L;

    @Getter
    private Long userId;

    private List<CartItem> cartItems;

    /**
     * Create a Cart for a new user
     */
    public Cart(Long userId) {
        this.userId = userId;
        this.cartItems = new ArrayList<>();
        this.setState(EntityState.Added);
    }

    /**
     * Create a Cart for an existing user
     */
    public Cart(Long userId, List<CartItem> cartItems) {
        this.userId = userId;
        this.cartItems = cartItems;
        this.setState(EntityState.Unchanged);
    }

    public CartItem addItem(Long itemId, Integer quantity) {
        for (var cartItem : this.getItems()) {
            if (cartItem.getItemId().equals(itemId)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return cartItem;
            }
        }

        var cartItem = new CartItem();
        cartItem.setUserId(this.userId);
        cartItem.setItemId(itemId);
        cartItem.setQuantity(quantity);
        cartItem.setState(EntityState.Added);
        this.cartItems.add(cartItem);
        this.fireIndexedPropertyChange("cartItems", this.cartItems.size() - 1, null, cartItem);
        return cartItem;
    }

    public List<CartItem> getItems() {
        return this.cartItems;
    }

    public void removeItem(CartItem cartItem) {
        cartItem.setQuantity(0);
        cartItem.setStatus(CartItemState.Deleted);
    }

    public void updateItem(CartItem cartItem, Integer newQuantity) {
        cartItem.setQuantity(newQuantity);
    }

}
