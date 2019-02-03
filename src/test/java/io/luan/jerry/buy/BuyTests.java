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
import io.luan.jerry.sell.dto.PublishItemDTO;
import io.luan.jerry.sell.service.SellService;
import io.luan.jerry.user.domain.User;
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

    @Autowired
    private SellService sellService;

    @Test
    public void testMain() {

        var user = new User();
        user.setId(1L);
        var title = "Item" + System.currentTimeMillis();
        var request = new PublishItemDTO();
        request.setCategoryId(1L);
        request.setTitle(title);
        request.setImgUrl("1.jpg");
        request.setPrice(99L);
        request.setInventory(33L);
        var item = sellService.publish(user, request);


        Long buyerId = 999L;
        var orderReq = new OrderDTO();
        orderReq.setUserId(buyerId);
        orderReq.setAddress("Address123");
        orderReq.getOrderLines().add(new OrderLineDTO(item.getId(), 5L));

        var order = buyService.createOrder(orderReq);

        Assert.assertNotNull(order.getId());
        Assert.assertEquals(1, order.getSubOrders().size());
        Assert.assertNotNull(order.getSubOrders().get(0).getId());
        Assert.assertEquals(Long.valueOf(5 * item.getPrice()), order.getTotalFee());
        Assert.assertEquals(order.getId(), order.getSubOrders().get(0).getParentId());

        System.out.println(order);
    }


    @Test
    public void testMainAndSub() {

        var user = new User();
        user.setId(1L);

        var title = "Item" + System.currentTimeMillis();
        var request = new PublishItemDTO();
        request.setCategoryId(1L);
        request.setTitle(title);
        request.setImgUrl("1.jpg");
        request.setPrice(99L);
        request.setInventory(33L);
        var item1 = sellService.publish(user, request);

        var title2 = "Item" + System.currentTimeMillis();
        var request2 = new PublishItemDTO();
        request2.setCategoryId(1L);
        request2.setTitle(title2);
        request2.setImgUrl("1.jpg");
        request2.setPrice(97L);
        request2.setInventory(33L);
        var item2 = sellService.publish(user, request2);


        Long userId = 999L;

        var orderReq = new OrderDTO();
        orderReq.setUserId(userId);
        orderReq.setAddress("Address123");
        orderReq.getOrderLines().add(new OrderLineDTO(item1.getId(), 5L));
        orderReq.getOrderLines().add(new OrderLineDTO(item2.getId(), 3L));

        Long totalFee = 5 * 99L + 3 * 97L;
        var order = buyService.createOrder(orderReq);

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
