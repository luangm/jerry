package io.luan.jerry.cart.service.impl;

import io.luan.jerry.cart.domain.Cart;
import io.luan.jerry.cart.domain.CartItem;
import io.luan.jerry.cart.dto.CartItemDTO;
import io.luan.jerry.cart.repository.CartRepository;
import io.luan.jerry.cart.service.CartService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem addToCart(CartItemDTO request) {
        var cart = getCart(request.getUserId());
        var cartItem = cart.addItem(request.getItemId(), request.getQuantity());
        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findById(userId);
    }

    @Override
    public CartItem remove(CartItemDTO request) {
        var cart = getCart(request.getUserId());
        CartItem existing = null;
        for (var cartItem : cart.getItems()) {
            if (cartItem.getItemId().equals(request.getItemId())) {
                existing = cartItem;
                cart.removeItem(existing);
            }
        }
        cartRepository.save(cart);
        return existing;
    }

    @Override
    public CartItem update(CartItemDTO request) {
        var cart = getCart(request.getUserId());
        CartItem existing = null;
        for (var cartItem : cart.getItems()) {
            if (cartItem.getItemId().equals(request.getItemId())) {
                existing = cartItem;
                cart.updateItem(existing, request.getQuantity());
            }
        }
        cartRepository.save(cart);
        return existing;
    }
}
