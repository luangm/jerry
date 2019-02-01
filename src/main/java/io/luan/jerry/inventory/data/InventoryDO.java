package io.luan.jerry.inventory.data;

import io.luan.jerry.inventory.domain.Inventory;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class InventoryDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long itemId;
    private Long available;
    private Long withheld;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;
    private Long version;

    public InventoryDO() {
        //
    }

    public InventoryDO(Inventory inventory) {
        this.id = inventory.getId();
        this.itemId = inventory.getItemId();
        this.available = inventory.getAvailable();
        this.withheld = inventory.getWithheld();
        this.gmtCreate = inventory.getGmtCreate();
        this.gmtModified = inventory.getGmtModified();
        this.version = inventory.getVersion();
    }

}
