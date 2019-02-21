package io.luan.jerry.item;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.service.CategoryService;
import io.luan.jerry.item.data.ItemMapper;
import io.luan.jerry.item.data.SkuDO;
import io.luan.jerry.item.data.SkuMapper;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.domain.Sku;
import io.luan.jerry.item.repository.ItemRepository;
import io.luan.jerry.item.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void itemSaveWithSku() {

        var name = "Category" + System.currentTimeMillis();
        var cat = new Category();
        cat.setName(name);
        cat.setIsLeaf(true);

        // Prop
        var prop1 = cat.addProperty(5L, "prop1");
        var prop2 = cat.addProperty(6L, "prop2");

        // values
        var value1 = prop1.addValue(11L, "value1");
        var value2 = prop1.addValue(12L, "value2");
        var value3 = prop2.addValue(13L, "value3");
        var value4 = prop2.addValue(14L, "value4");

        categoryService.save(cat);

        var title = "Item" + System.currentTimeMillis();

        var item = new Item();
        item.setCategoryId(cat.getId());
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);

        var sku = new Sku();
        sku.setPrice(330L);
        sku.setInventoryId(5L);
        sku.setPropertyValue(5L, 11L);
        sku.setPropertyValue(6L, 13L);
        item.addSku(sku);

        var sku2 = new Sku();
        sku2.setPrice(220L);
        sku2.setInventoryId(6L);
        sku2.setPropertyValue(5L, 12L);
        sku2.setPropertyValue(6L, 14L);
        item.addSku(sku2);

        itemService.save(item);


        var fromDb = itemService.findById(item.getId(), true);
        System.out.println(fromDb);
        System.out.println(item);

        Assertions.assertEquals(fromDb, item);
    }

    @Test
    public void mapperSave() {

        var title = "Item" + System.currentTimeMillis();

        var item = new Item();
        item.setCategoryId(1L);
        item.setTitle(title);
        item.setImgUrl("http://www.baidu.com/logo.jpg");
        item.setPrice(100L);
        item.setUserId(1L);
        item = itemService.save(item);

        var sku = new Sku();
        sku.setItem(item);
        sku.setPrice(330L);
        sku.setInventoryId(5L);
        sku.setPropertyValue(5L, 3L);
        sku.setPropertyValue(6L, 4L);

        var skuDO = new SkuDO(sku);
        skuMapper.insert(skuDO);
    }

}
