package io.luan.jerry.buy;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.dto.SubOrderDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.repository.ItemRepository;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import io.luan.jerry.order.repository.OrderRepository;
import io.luan.jerry.order.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyTests {

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
    public void testMain() {

        var title = "Item" + System.currentTimeMillis();

        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);
        itemRepository.save(item);

        Long userId = 999L;

        var request = new OrderDTO();
        request.setUserId(userId);
        request.getSubOrders().add(new SubOrderDTO(item.getId(), 5));

        var order = buyService.createOrder(request);

        Assert.assertNotNull(order.getId());
        Assert.assertEquals(1, order.getSubOrders().size());
        Assert.assertNotNull(order.getSubOrders().get(0).getId());
        Assert.assertEquals(Long.valueOf(5 * item.getPrice()), order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(0).getParentId());

        System.out.println(order);
    }


    @Test
    public void testMainAndSub() {

        var title = "Item1" + System.currentTimeMillis();
        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);
        itemRepository.save(item);

        var title2 = "Item22" + System.currentTimeMillis();
        var item2 = new Item();
        item2.setCategoryId(1L);
        item2.setTitle(title2);
        item2.setImgUrl("logo2");
        item2.setPrice(10L);
        item2.setUserId(5L);
        itemRepository.save(item2);


        Long userId = 999L;

        var request = new OrderDTO();
        request.setUserId(userId);
        request.getSubOrders().add(new SubOrderDTO(item.getId(), 5));
        request.getSubOrders().add(new SubOrderDTO(item2.getId(), 3));

        Long totalFee = 5 * 100L + 3 * 10L;
        var order = buyService.createOrder(request);

        System.out.println(order);

        Assert.assertNotNull(order.getId());
        Assert.assertEquals(2, order.getSubOrders().size());
        Assert.assertNotNull(order.getSubOrders().get(0).getId());
        Assert.assertNotNull(order.getSubOrders().get(1).getId());
        Assert.assertEquals(totalFee, order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(0).getParentId());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), order.getSubOrders().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), order.getSubOrders().get(1).getBuyerId());


        var orderFromDb = orderRepository.findById(order.getId());
        Assert.assertNotNull(orderFromDb);
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getBuyerId());
        Assert.assertEquals(2, orderFromDb.getSubOrders().size());
        Assert.assertEquals(order.getSubOrders().get(0).getId(), orderFromDb.getSubOrders().get(0).getId());
        Assert.assertEquals(totalFee, orderFromDb.getTotalFee());
        Assert.assertEquals(order.getId(), orderFromDb.getSubOrders().get(0).getParentId());
        Assert.assertEquals(order.getId(), orderFromDb.getSubOrders().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getSubOrders().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getSubOrders().get(1).getBuyerId());

        System.out.println(order);
        System.out.println(orderFromDb);
    }
}
