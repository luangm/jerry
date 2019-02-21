package io.luan.jerry.sell;

import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.sell.dto.PublishItemRequest;
import io.luan.jerry.sell.service.SellService;
import io.luan.jerry.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SellTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellService sellService;

    @Autowired
    private InventoryService inventoryService;

    @Test
    void sellInventory() {
        var title = "Item" + System.currentTimeMillis();

        var request = new PublishItemRequest();
        request.setCategoryId(1L);
        request.setTitle(title);
        request.setImgUrl("1.jpg");
        request.setPrice(99L);
        request.setInventory(33L);

        var user = new User();
        user.setId(1L);

        var item = sellService.publish(user, request);

        var inventory = inventoryService.findById(item.getInventoryId());

        assertEquals(Long.valueOf(33), inventory.getAvailable());

        inventory.allocate(500);

        assertEquals(Long.valueOf(533), inventory.getAvailable());

        inventoryService.save(inventory);

        assertEquals(Long.valueOf(2), inventory.getVersion());
    }

}
