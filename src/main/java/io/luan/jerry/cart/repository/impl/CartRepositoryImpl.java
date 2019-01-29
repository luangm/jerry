package io.luan.jerry.cart.repository.impl;

import io.luan.jerry.cart.data.CartItemDO;
import io.luan.jerry.cart.data.CartItemMapper;
import io.luan.jerry.cart.domain.Cart;
import io.luan.jerry.cart.domain.CartItem;
import io.luan.jerry.cart.domain.CartItemState;
import io.luan.jerry.cart.factory.CartItemFactory;
import io.luan.jerry.cart.repository.CartRepository;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final CartItemMapper cartItemMapper;

    private final CartItemFactory cartItemFactory;

    @Autowired
    public CartRepositoryImpl(CartItemMapper cartItemMapper, CartItemFactory cartItemFactory) {
        this.cartItemMapper = cartItemMapper;
        this.cartItemFactory = cartItemFactory;
    }

    @Override
    public boolean delete(Cart entity) {
        throw new UnsupportedOperationException("Delete a cart is not supported.");
    }

    @Override
    public List<Cart> findAll() {
        throw new UnsupportedOperationException("Find all carts is not supported.");
    }

    @Override
    public Cart findById(@NotNull Long userId) {
        List<CartItem> cartItems = new ArrayList<>();
        for (var cartItemDO : cartItemMapper.findAllByUserId(userId)) {
            var cartItem = cartItemFactory.load(cartItemDO);
            cartItems.add(cartItem);
        }

        if (cartItems.size() > 0) {
            return new Cart(userId, cartItems);
        } else {
            return new Cart(userId);
        }
    }

    @Override
    @Transactional
    public void save(Cart cart) {
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            var cartItem = iterator.next();

            var cartItemDO = new CartItemDO(cartItem);
            if (cartItem.getState() == EntityState.Added) {
                cartItemMapper.insert(cartItemDO);
                cartItem.setId(cartItemDO.getId());
                cartItem.setState(EntityState.Unchanged);
            } else if (cartItem.getState() == EntityState.Modified) {
                cartItemMapper.update(cartItemDO);
                cartItem.setState(EntityState.Unchanged);

                if (cartItem.getStatus() == CartItemState.Deleted) {
                    iterator.remove(); // safe remove
                }
            }
        }
    }
}
