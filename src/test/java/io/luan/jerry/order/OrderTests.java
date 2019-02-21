package io.luan.jerry.order;

import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.repository.ItemRepository;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderLine;
import io.luan.jerry.order.repository.OrderRepository;
import io.luan.jerry.order.service.OrderService;
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
    public void testSelectAll() {

        var orders = orderRepository.findAll();
        System.out.println(orders);

    }


    @Test
    public void orderRepoTestMain() {

        Long userId = 999L;
        Long itemId = 321L;
        Long sellerId = 555L;

        var order = new Order();
        order.setBuyerId(userId);
        order.setSellerId(sellerId);

        var orderLine = new OrderLine();
        orderLine.setSellerId(sellerId);
        orderLine.setItemId(itemId);
        orderLine.setQuantity(5L);
        orderLine.setItemPrice(100L);
        orderLine.setItemTitle("A");
        orderLine.setItemImgUrl("B");
        orderLine.setDiscountFee(0L);

        order.addOrderLine(orderLine);

        orderRepository.save(order);
        Assert.assertNotNull(order.getId());
        Assert.assertEquals(1, order.getOrderLines().size());
        Assert.assertNotNull(order.getOrderLines().get(0).getId());
        Assert.assertEquals(Long.valueOf(500L), order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getOrderLines().get(0).getParentId());


        var orderFromDb = orderRepository.findById(order.getId());
        Assert.assertNotNull(orderFromDb);
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getBuyerId());
        Assert.assertEquals(1, orderFromDb.getOrderLines().size());
        Assert.assertEquals(order.getOrderLines().get(0).getId(), orderFromDb.getOrderLines().get(0).getId());
        Assert.assertEquals(Long.valueOf(500L), orderFromDb.getTotalFee());
    }


    @Test
    public void orderRepoTestMainAnd2Subs() {

        Long userId = 999L;

        var order = new Order();
        order.setBuyerId(userId);
        order.setSellerId(555L);

        var orderLine = new OrderLine();
        orderLine.setBuyerId(999L);
        orderLine.setSellerId(555L);
        orderLine.setItemId(321L);
        orderLine.setQuantity(5L);
        orderLine.setItemPrice(100L);
        order.addOrderLine(orderLine);

        var orderLine1 = new OrderLine();
        orderLine1.setBuyerId(999L);
        orderLine1.setSellerId(555L);
        orderLine1.setItemId(444L);
        orderLine1.setQuantity(3L);
        orderLine1.setItemPrice(100L);
        order.addOrderLine(orderLine1);

        orderRepository.save(order);

        Assert.assertNotNull(order.getId());
        Assert.assertEquals(2, order.getOrderLines().size());
        Assert.assertNotNull(order.getOrderLines().get(0).getId());
        Assert.assertNotNull(order.getOrderLines().get(1).getId());
        Assert.assertEquals(Long.valueOf(800L), order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getOrderLines().get(0).getParentId());
        Assert.assertEquals(order.getId(), order.getOrderLines().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), order.getOrderLines().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), order.getOrderLines().get(1).getBuyerId());


        var orderFromDb = orderRepository.findById(order.getId());
        Assert.assertNotNull(orderFromDb);
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getBuyerId());
        Assert.assertEquals(2, orderFromDb.getOrderLines().size());
        Assert.assertEquals(order.getOrderLines().get(0).getId(), orderFromDb.getOrderLines().get(0).getId());
        Assert.assertEquals(Long.valueOf(800L), orderFromDb.getTotalFee());
        Assert.assertEquals(order.getId(), orderFromDb.getOrderLines().get(0).getParentId());
        Assert.assertEquals(order.getId(), orderFromDb.getOrderLines().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getOrderLines().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getOrderLines().get(1).getBuyerId());

        System.out.println(order);
        System.out.println(orderFromDb);
    }
}
