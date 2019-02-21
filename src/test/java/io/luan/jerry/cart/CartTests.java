package io.luan.jerry.cart;

import io.luan.jerry.cart.domain.Cart;
import io.luan.jerry.cart.domain.CartItemState;
import io.luan.jerry.cart.dto.CartItemDTO;
import io.luan.jerry.cart.repository.CartRepository;
import io.luan.jerry.cart.service.CartService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void repoSave() {

        Long userId = System.currentTimeMillis() / 1000;
        Long itemId = 123L;
        Long item2Id = 234L;
        Long item3Id = 345L;

        var cart = new Cart(userId);
        cart.addItem(itemId, 0L,2L);
        cart.addItem(item2Id, 0L,3L);

        cartRepository.save(cart);
        System.out.println(cart);

        // Modify already saved
        var item0 = cart.getItems().get(0);
        var item1 = cart.getItems().get(1);
        item0.setQuantity(10L);
        item1.setQuantity(5L);

        // add a new one
        cart.addItem(item3Id,0L, 7L);

        cartRepository.save(cart);
        System.out.println(cart);

        cart.addItem(itemId,0L, 10L);
        Assert.assertEquals(3, cart.getItems().size());
        cartRepository.save(cart);
    }

    @Test
    public void serviceTest() {

        Long userId = System.currentTimeMillis() / 1000;
        Long itemId = 123L;
        Long item2Id = 234L;
        Long item3Id = 345L;

        var item1 = cartService.addToCart(new CartItemDTO(userId, itemId, 0L, 3L));
        var item2 = cartService.addToCart(new CartItemDTO(userId, item2Id,0L, 4L));
        Assert.assertNotNull(item1.getId());
        Assert.assertNotNull(item2.getId());

        cartService.update(new CartItemDTO(userId, itemId, 0L,5L));

        var cart = cartService.getCart(userId);
        Assert.assertEquals(2, cart.getItems().size());
        Assert.assertEquals(Long.valueOf(5), cart.getItems().get(0).getQuantity());
        Assert.assertEquals(Long.valueOf(4), cart.getItems().get(1).getQuantity());
    }

    @Test
    public void removeTest() {

        Long userId = System.currentTimeMillis() / 1000 + 55;
        Long itemId = 123L;

        var item1 = cartService.addToCart(new CartItemDTO(userId, itemId,0L, 3L));
        Assert.assertNotNull(item1.getId());

        cartService.remove(new CartItemDTO(userId, itemId,0L, 0L)); // quantity doesn't matter

        var cart = cartService.getCart(userId);
        Assert.assertEquals(0, cart.getItems().size());

        // add again
        var item2 = cartService.addToCart(new CartItemDTO(userId, itemId, 0L,4L));
        Assert.assertNotNull(item2.getId());

        Assert.assertNotEquals(item2.getId(), item1.getId());

        var cart2 = cartService.getCart(userId);
        Assert.assertEquals(1, cart2.getItems().size());
    }
}
