package io.luan.jerry.order;

import io.luan.jerry.buy.dto.CreateOrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.domain.Item;
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
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BuyService buyService;

    @Test
    public void createThenQueryThenDelete() {

        Long userId = 999L;

        var title = "Item" + System.currentTimeMillis();
        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(101L);
        item.setUserId(1L);
        item = itemService.publish(item);


        var request = new CreateOrderDTO();
        request.setUserId(userId);
        request.setItemId(item.getId());
        request.setAmount(5);

        var order = buyService.createOrder(request);
        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getId());
        Assert.assertEquals(order.getItemId(), item.getId());
        Assert.assertEquals(order.getBuyerId(), userId);
        Assert.assertEquals(Long.valueOf(505L), order.getTotalFee());
        Assert.assertEquals(Integer.valueOf(5), order.getAmount());

        var success = orderRepository.delete(order);
        Assert.assertTrue(success);

        var orderByIdAfterDelete = orderService.findById(order.getId());
        Assert.assertNull(orderByIdAfterDelete);

        itemRepository.delete(item);
    }

}
