package io.luan.jerry.cart.factory;

import io.luan.jerry.cart.data.CartItemDO;
import io.luan.jerry.cart.domain.CartItem;
import io.luan.jerry.cart.domain.CartItemState;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.stereotype.Component;

@Component
public class CartItemFactory {

    public CartItem loadFromDataObject(CartItemDO cartItemDO) {
        var cartItem = new CartItem();
        cartItem.setId(cartItemDO.getId());
        cartItem.setUserId(cartItemDO.getUserId());
        cartItem.setItemId(cartItemDO.getItemId());
        cartItem.setQuantity(cartItemDO.getQuantity());
        cartItem.setGmtCreate(cartItemDO.getGmtCreate());
        cartItem.setGmtModified(cartItemDO.getGmtModified());
        cartItem.setStatus(CartItemState.fromValue(cartItemDO.getStatus()));
        cartItem.setState(EntityState.Unchanged);
        return cartItem;
    }
}
