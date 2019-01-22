package io.luan.jerry.order;

import io.luan.jerry.buy.service.BuyService;
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

        var subOrder = new SubOrder();
        subOrder.setSellerId(sellerId);
        subOrder.setItemId(itemId);
        subOrder.setQuantity(5);
        subOrder.setItemPrice(100L);
        subOrder.setItemTitle("A");
        subOrder.setItemImgUrl("B");
        subOrder.setDiscountFee(0L);

        order.addSubOrder(subOrder);

        orderRepository.save(order);
        Assert.assertNotNull(order.getId());
        Assert.assertEquals(1, order.getSubOrders().size());
        Assert.assertNotNull(order.getSubOrders().get(0).getId());
        Assert.assertEquals(Long.valueOf(500L), order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(0).getParentId());


        var orderFromDb = orderRepository.findById(order.getId());
        Assert.assertNotNull(orderFromDb);
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getBuyerId());
        Assert.assertEquals(1, orderFromDb.getSubOrders().size());
        Assert.assertEquals(order.getSubOrders().get(0).getId(), orderFromDb.getSubOrders().get(0).getId());
        Assert.assertEquals(Long.valueOf(500L), orderFromDb.getTotalFee());
    }


    @Test
    public void orderRepoTestMainAnd2Subs() {

        Long userId = 999L;

        var order = new Order();
        order.setBuyerId(userId);
        order.setSellerId(555L);

        var subOrder = new SubOrder();
        subOrder.setBuyerId(999L);
        subOrder.setSellerId(555L);
        subOrder.setItemId(321L);
        subOrder.setQuantity(5);
        subOrder.setItemPrice(100L);
        order.addSubOrder(subOrder);

        var subOrder2 = new SubOrder();
        subOrder2.setBuyerId(999L);
        subOrder2.setSellerId(555L);
        subOrder2.setItemId(444L);
        subOrder2.setQuantity(3);
        subOrder2.setItemPrice(100L);
        order.addSubOrder(subOrder2);

        orderRepository.save(order);

        Assert.assertNotNull(order.getId());
        Assert.assertEquals(2, order.getSubOrders().size());
        Assert.assertNotNull(order.getSubOrders().get(0).getId());
        Assert.assertNotNull(order.getSubOrders().get(1).getId());
        Assert.assertEquals(Long.valueOf(800L), order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(0).getParentId());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), order.getSubOrders().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), order.getSubOrders().get(1).getBuyerId());


        var orderFromDb = orderRepository.findById(order.getId());
        Assert.assertNotNull(orderFromDb);
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getBuyerId());
        Assert.assertEquals(2, orderFromDb.getSubOrders().size());
        Assert.assertEquals(order.getSubOrders().get(0).getId(), orderFromDb.getSubOrders().get(0).getId());
        Assert.assertEquals(Long.valueOf(800L), orderFromDb.getTotalFee());
        Assert.assertEquals(order.getId(), orderFromDb.getSubOrders().get(0).getParentId());
        Assert.assertEquals(order.getId(), orderFromDb.getSubOrders().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getSubOrders().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getSubOrders().get(1).getBuyerId());

        System.out.println(order);
        System.out.println(orderFromDb);
    }
}
