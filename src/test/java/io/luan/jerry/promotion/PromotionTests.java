package io.luan.jerry.promotion;

import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.buy.dto.OrderLineDTO;
import io.luan.jerry.buy.service.BuyService;
import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.promotion.data.PromotionMapper;
import io.luan.jerry.promotion.domain.Promotion;
import io.luan.jerry.promotion.dto.PublishPromotionDTO;
import io.luan.jerry.promotion.repository.PromotionRepository;
import io.luan.jerry.promotion.service.PromotionService;
import io.luan.jerry.sell.dto.PublishItemRequest;
import io.luan.jerry.sell.service.SellService;
import io.luan.jerry.user.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PromotionTests {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private BuyService buyService;

    @Autowired
    private SellService sellService;

    @Test
    public void publishTest() {
        var title = "Item" + System.currentTimeMillis();

        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);

        item = itemService.save(item);

        var itemId = item.getId();
        var newPrice = 95L;
        var startTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        var endTime = LocalDateTime.of(2019, 12, 31, 23, 59, 59);

        var request = new PublishPromotionDTO();
        request.setItemId(itemId);
        request.setNewPrice(newPrice);
        request.setStartTime(startTime);
        request.setEndTime(endTime);

        var promotion = promotionService.publish(request);
        System.out.println(promotion);

        var list = promotionService.findByItemId(item.getId());
        Assert.assertEquals(1, list.size());

    }

    @Test
    public void publishTestBuy() {

        // build item
        var user = new User();
        user.setId(1L);

        var title = "Item" + System.currentTimeMillis();
        var request = new PublishItemRequest();
        request.setCategoryId(1L);
        request.setTitle(title);
        request.setImgUrl("1.jpg");
        request.setPrice(99L);
        request.setInventory(33L);
        var item1 = sellService.publish(user, request);


        // Build promo
        var itemId = item1.getId();
        var newPrice = 95L;
        var startTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        var endTime = LocalDateTime.of(2019, 12, 31, 23, 59, 59);
        var promoRequest = new PublishPromotionDTO();
        promoRequest.setItemId(itemId);
        promoRequest.setNewPrice(newPrice);
        promoRequest.setStartTime(startTime);
        promoRequest.setEndTime(endTime);

        var promotion = promotionService.publish(promoRequest);
        System.out.println(promotion);

        var list = promotionService.findByItemId(item1.getId());
        Assert.assertEquals(1, list.size());

        var buyRequest = new OrderDTO();
        buyRequest.setAddress("Address123");
        buyRequest.setUserId(1L);
        buyRequest.getOrderLines().add(new OrderLineDTO(item1.getId(), 5L));


        var order = buyService.createOrder(buyRequest);
        Assert.assertNotNull(order);
        Assert.assertEquals(Long.valueOf(95L * 5), order.getTotalFee());
        Assert.assertEquals(Long.valueOf(4L * 5), order.getOrderLines().get(0).getDiscountFee());
    }

    @Test
    public void repoFindAll() {

        List<Long> itemIds = new ArrayList<>();
        itemIds.add(99L);
        itemIds.add(100L);
        itemIds.add(101L);
        itemIds.add(102L);

        var list = promotionRepository.findByItemIds(itemIds);
        System.out.println(list);
    }

    @Test
    public void repoSave() {

        var itemId = 123L;
        var newPrice = 222L;
        var startTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        var endTime = LocalDateTime.of(2019, 12, 31, 23, 59, 59);

        var promotion = new Promotion();
        promotion.setItemId(itemId);
        promotion.setNewPrice(newPrice);
        promotion.setStartTime(startTime);
        promotion.setEndTime(endTime);

        promotionRepository.save(promotion);
        System.out.println(promotion);
        Assert.assertNotNull(promotion.getId());
        Assert.assertEquals(EntityState.Unchanged, promotion.getState());

        promotion.setNewPrice(333L);
        Assert.assertEquals(EntityState.Modified, promotion.getState());

        promotionRepository.save(promotion);
        Assert.assertEquals(EntityState.Unchanged, promotion.getState());
        System.out.println(promotion);

        var promotionFromDb = promotionRepository.findById(promotion.getId());
        Assert.assertNotNull(promotionFromDb);
        Assert.assertEquals(EntityState.Unchanged, promotionFromDb.getState());

        Assert.assertEquals(promotion.getItemId(), promotionFromDb.getItemId());
        Assert.assertEquals(promotion.getNewPrice(), promotionFromDb.getNewPrice());
        Assert.assertEquals(promotion.getStartTime(), promotionFromDb.getStartTime());
        Assert.assertEquals(promotion.getEndTime(), promotionFromDb.getEndTime());
    }
}
