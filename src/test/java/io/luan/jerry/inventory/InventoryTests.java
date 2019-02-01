package io.luan.jerry.inventory;

import io.luan.jerry.common.repository.VersionConflictException;
import io.luan.jerry.inventory.data.InventoryDO;
import io.luan.jerry.inventory.data.InventoryMapper;
import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.repository.InventoryRepository;
import io.luan.jerry.inventory.service.InventoryService;
import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.sell.service.SellService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InventoryTests {

    @Autowired
    private InventoryMapper mapper;

    @Autowired
    private InventoryRepository repository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellService sellService;

    @Autowired
    private InventoryService inventoryService;

    @Test
    void freezeThenReduce() {
        var inventory = new Inventory();
        inventory.setItemId(123L);
        inventory.setAvailable(24L);
        System.out.println(inventory);

        repository.save(inventory); // initial

        var success = inventory.freeze(5); // at create order
        assertTrue(success);
        assertEquals(Integer.valueOf(24 - 5), inventory.getAvailable());
        assertEquals(Integer.valueOf(5), inventory.getWithheld());

        repository.save(inventory);

        success = inventory.reduce(5);
        assertTrue(success);
        assertEquals(Integer.valueOf(24 - 5), inventory.getAvailable());
        assertEquals(Integer.valueOf(0), inventory.getWithheld());
        repository.save(inventory);

    }

    @Test
    void freezeThenRelease() {
        var inventory = new Inventory();
        inventory.setItemId(123L);
        inventory.setAvailable(24L);
        System.out.println(inventory);

        repository.save(inventory); // initial

        var success = inventory.freeze(5); // at create order
        assertTrue(success);
        assertEquals(Integer.valueOf(24 - 5), inventory.getAvailable());
        assertEquals(Integer.valueOf(5), inventory.getWithheld());

        repository.save(inventory);

        success = inventory.release(5);
        assertTrue(success);
        assertEquals(Integer.valueOf(24), inventory.getAvailable());
        assertEquals(Integer.valueOf(0), inventory.getWithheld());
        repository.save(inventory);
    }

    @Test
    void mapperSave() {
        var inventory = new Inventory();
        inventory.setItemId(123L);
        inventory.setAvailable(33L);

        var inventoryDO = new InventoryDO(inventory);
        mapper.insert(inventoryDO);

    }

    @Test
    void repoSave() {
        var inventory = new Inventory();
        inventory.setItemId(123L);
        inventory.setAvailable(33L);

        repository.save(inventory);

        assertNotNull(inventory.getId());
        System.out.println(inventory);
    }

    @Test
    void updateVersion() {
        var inventory = new Inventory();
        inventory.setItemId(123L);
        inventory.setAvailable(33L);

        System.out.println(inventory);
        assertEquals(Integer.valueOf(0), inventory.getVersion()); // Unsaved

        repository.save(inventory);

        assertNotNull(inventory.getId());
        assertEquals(Integer.valueOf(1), inventory.getVersion()); // Just saved

        inventory.allocate(10);
        assertEquals(Integer.valueOf(33 + 10), inventory.getAvailable()); // Added
        repository.save(inventory);

        assertEquals(Integer.valueOf(2), inventory.getVersion()); // Updated

        // Try failed update
        inventory.setWithheld(10L);
        inventory.setVersion(1L);

        assertThrows(VersionConflictException.class, () -> {
            repository.save(inventory); // Should throw exception
        });

        var fromDb = repository.findById(inventory.getId());
        assertEquals(Integer.valueOf(2), fromDb.getVersion());
    }

}
