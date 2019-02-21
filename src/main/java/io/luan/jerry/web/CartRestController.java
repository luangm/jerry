package io.luan.jerry.web;

import io.luan.jerry.cart.dto.CartItemDTO;
import io.luan.jerry.cart.service.CartService;
import io.luan.jerry.cart.vm.CartItemVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    private final CartService cartService;

    @Autowired
    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add")
    public CartItemVM add(@RequestBody CartItemVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var cartItemDTO = new CartItemDTO(user.getId(), vm.getItemId(), vm.getSkuId(), vm.getQuantity());
        var entity = cartService.addToCart(cartItemDTO);

        return new CartItemVM(entity);
    }

    @PostMapping("/cart/change")
    public CartItemVM change(@RequestBody CartItemVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var cartItemDTO = new CartItemDTO(user.getId(), vm.getItemId(), vm.getSkuId(), vm.getQuantity());
        var entity = cartService.update(cartItemDTO);

        return new CartItemVM(entity);
    }

    @PostMapping("/cart/remove")
    public CartItemVM remove(@RequestBody CartItemVM vm) {
        var user = SecurityUtils.getCurrentUser();

        var cartItemDTO = new CartItemDTO(user.getId(), vm.getItemId(), vm.getSkuId(), vm.getQuantity());
        var entity = cartService.remove(cartItemDTO);

        return new CartItemVM(entity);
    }
}
