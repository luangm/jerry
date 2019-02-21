package io.luan.jerry.item.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class Sku extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * id for the sku
     */
    private Long id;

    /**
     * parent Item
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Item item;

    /**
     * Image of the sku, if available
     */
    private String imgUrl;

    /**
     * Price in cents
     */
    private Long price;

    /**
     * Inventory ID
     */
    private Long inventoryId = 0L;

    /**
     * Map of PV pairs
     */
    private Map<Long, Long> propertyMap = new HashMap<>();

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public Sku() {
        //
    }

    public void setImgUrl(String newValue) {
        if (newValue != null && !newValue.equals(this.imgUrl)) {
            firePropertyChange("imgUrl", imgUrl, newValue);
            this.imgUrl = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setInventoryId(Long newValue) {
        if (!newValue.equals(this.inventoryId)) {
            firePropertyChange("inventoryId", inventoryId, newValue);
            this.inventoryId = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setPrice(Long newValue) {
        if (!newValue.equals(this.price)) {
            firePropertyChange("price", price, newValue);
            this.price = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setPropertyValue(Long propertyId, Long valueId) {
        var existing = propertyMap.get(propertyId);

        // Remove existing
        if (valueId == null && existing != null) {
            fireMappedPropertyChange("propertyMap", propertyId, existing, valueId);
            propertyMap.remove(propertyId);
            this.gmtModified = OffsetDateTime.now().withNano(0);
            return;
        }

        // Set new value
        if (valueId != null && !valueId.equals(existing)) {
            fireMappedPropertyChange("propertyMap", propertyId, existing, valueId);
            propertyMap.put(propertyId, valueId);
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

}
