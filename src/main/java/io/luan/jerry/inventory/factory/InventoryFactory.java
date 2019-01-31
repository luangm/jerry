package io.luan.jerry.inventory.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.inventory.data.InventoryDO;
import io.luan.jerry.inventory.domain.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryFactory {

    public Inventory load(InventoryDO inventoryDO) {
        var inventory = new Inventory();
        inventory.setId(inventoryDO.getId());
        inventory.setItemId(inventoryDO.getItemId());
        inventory.setAvailable(inventoryDO.getAvailable());
        inventory.setWithheld(inventoryDO.getWithheld());
        inventory.setVersion(inventoryDO.getVersion());
        inventory.setGmtCreate(inventoryDO.getGmtCreate());

        // Note: GmtModified and State should ALWAYS be set last in that order
        inventory.setGmtModified(inventoryDO.getGmtModified());
        inventory.setState(EntityState.Unchanged);
        return inventory;
    }
}
