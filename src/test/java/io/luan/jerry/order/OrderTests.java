package io.luan.jerry.order;

import io.luan.jerry.item.repository.ItemRepository;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.repository.OrderRepository;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.user.repository.UserRepository;
import io.luan.jerry.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void createThenQueryThenDelete() {
        var nick = "User" + System.currentTimeMillis();
        var user = userService.create(nick);

        var title = "Item" + System.currentTimeMillis();
        var item = itemService.create(title, 100L);

        var order = orderService.create(user.getId(), item.getId());
        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getId());
        Assert.assertEquals(order.getItemId(), item.getId());
        Assert.assertEquals(order.getUserId(), user.getId());

        var success = orderRepository.delete(order);
        Assert.assertTrue(success);

        var orderByIdAfterDelete = orderService.findById(order.getId());
        Assert.assertNull(orderByIdAfterDelete);

        userRepository.delete(user);
        itemRepository.delete(item);
    }

}