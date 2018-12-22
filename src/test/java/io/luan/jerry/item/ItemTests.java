package io.luan.jerry.item;

import io.luan.jerry.item.data.ItemMapper;
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
        var item = itemService.create(title, 100L);
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
        var title = "Item" + System.currentTimeMillis();
        var item = itemService.create(title, 100L);

        var title2 = "Item" + System.currentTimeMillis();
        var item2 = itemService.create(title2, 100L);

        var title3 = "Item" + System.currentTimeMillis();
        var item3 = itemService.create(title3, 100L);

        var items = itemService.findAll();
        Assert.assertNotNull(items);
        Assert.assertEquals(3, items.size());

        itemRepository.delete(item);
        itemRepository.delete(item2);
        itemRepository.delete(item3);
    }

    @Before
    public void cleanDb() {
        itemMapper.unsafeDeleteAll();
    }
}
