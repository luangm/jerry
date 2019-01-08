package io.luan.jerry.cart.vm;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartOrderVM implements Serializable {

    private List<CartItemVM> cartItems = new ArrayList<>();

}
