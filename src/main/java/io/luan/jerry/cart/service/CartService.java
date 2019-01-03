package io.luan.jerry.cart.service;

import io.luan.jerry.cart.domain.Cart;
import io.luan.jerry.cart.domain.CartItem;
import io.luan.jerry.cart.dto.CartItemDTO;

public interface CartService {

    /**
     * Add Item to Cart
     */
    CartItem addToCart(CartItemDTO request);

    Cart getCart(Long userId);

    /**
     * Remove from cart
     */
    CartItem remove(CartItemDTO request);

    /**
     * Modify Item's quantity
     */
    CartItem update(CartItemDTO request);
}
