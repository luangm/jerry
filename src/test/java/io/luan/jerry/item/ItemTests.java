package io.luan.jerry.item;

import io.luan.jerry.item.data.ItemMapper;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.repository.ItemRepository;
import io.luan.jerry.item.service.ItemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void createItemThenQueryThenDelete() {

        var title = "Item" + System.currentTimeMillis();

        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);

        item = itemService.save(item);
        Assert.assertNotNull(item);

        Long itemId = item.getId();
        Assert.assertNotNull(itemId);

        var itemById = itemService.findById(itemId);
        Assert.assertNotNull(itemById);
        Assert.assertEquals(title, itemById.getTitle());

        var success = itemRepository.delete(item);
        Assert.assertTrue(success);

        var itemByIdAfterDelete = itemService.findById(itemId);
        Assert.assertNull(itemByIdAfterDelete);
    }

    @Test
    public void findAll() {
        itemMapper.unsafeDeleteAll();

        var title = "Item" + System.currentTimeMillis();
        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);
        item = itemService.save(item);

        var title2 = "Item" + System.currentTimeMillis();
        var item2 = new Item();
        item2.setCategoryId(1L);
        item2.setTitle(title2);
        item2.setImgUrl("http://www.baidu.com/logo.jpg");
        item2.setPrice(100L);
        item2.setUserId(1L);
        item2 = itemService.save(item2);

        var title3 = "Item" + System.currentTimeMillis();
        var item3 = new Item();
        item3.setCategoryId(1L);
        item3.setTitle(title3);
        item3.setImgUrl("http://www.baidu.com/logo.jpg");
        item3.setPrice(100L);
        item3.setUserId(1L);
        item3 = itemService.save(item3);

        var items = itemService.findAll();
        Assert.assertNotNull(items);
        Assert.assertEquals(3, items.size());

        itemRepository.delete(item);
        itemRepository.delete(item2);
        itemRepository.delete(item3);
    }


}
