package io.luan.jerry.buy;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.dto.OrderLineDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.repository.ItemRepository;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.order.repository.OrderRepository;
import io.luan.jerry.order.service.OrderService;
import io.luan.jerry.payment.domain.PaymentStatus;
import io.luan.jerry.payment.service.PaymentService;
import io.luan.jerry.payment.vm.PayVM;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Autowired
    private PaymentService paymentService;

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
        request.getOrderLines().add(new OrderLineDTO(item.getId(), 5));

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
        item.setUserId(5L);
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
        request.getOrderLines().add(new OrderLineDTO(item.getId(), 5));
        request.getOrderLines().add(new OrderLineDTO(item2.getId(), 3));

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
        Assert.assertEquals(order.getSellerId(), orderFromDb.getSellerId());
        Assert.assertEquals(2, orderFromDb.getSubOrders().size());
        Assert.assertEquals(order.getSubOrders().get(0).getId(), orderFromDb.getSubOrders().get(0).getId());
        Assert.assertEquals(totalFee, orderFromDb.getTotalFee());
        Assert.assertEquals(order.getId(), orderFromDb.getSubOrders().get(0).getParentId());
        Assert.assertEquals(order.getId(), orderFromDb.getSubOrders().get(1).getParentId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getSubOrders().get(0).getBuyerId());
        Assert.assertEquals(order.getBuyerId(), orderFromDb.getSubOrders().get(1).getBuyerId());

        System.out.println(order);
        System.out.println(orderFromDb);


        var payment = paymentService.findByOrderId(order.getId());
        Assert.assertNotNull(payment);
        Assert.assertEquals(payment.getOrderId(), order.getId());
        Assert.assertEquals(payment.getPayerId(), order.getBuyerId());
        Assert.assertEquals(payment.getPayeeId(), order.getSellerId());
        Assert.assertEquals(payment.getTotalFee(), order.getTotalFee());
        Assert.assertEquals(payment.getStatus(), order.getPayStatus());

        System.out.println(payment);

        Assert.assertEquals(PaymentStatus.Created, orderFromDb.getPayStatus());

        var payVM = new PayVM();
        payVM.setPaymentId(payment.getId());
        payVM.setPassword("111");
        var paidOrder = buyService.payOrder(payVM);
        Assert.assertNotNull(paidOrder);
        Assert.assertEquals(paidOrder.getPayStatus(), PaymentStatus.Paid);

        var paidOrderFromDb = orderService.findById(paidOrder.getId());
        Assert.assertEquals(PaymentStatus.Paid, paidOrderFromDb.getPayStatus());
    }
}
