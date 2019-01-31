package io.luan.jerry.inventory.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Inventory extends Entity {

    private Long id;

    private Long itemId;

    private Integer available = 0;

    private Integer withheld = 0;

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    /**
     * Version No. for optimistic locking
     * Default version after insert is 1
     * If version = 0 or less, then it's not saved
     */
    private Long version = 0L;

    /**
     * Additional Quantity
     */
    public boolean allocate(int quantity) {
        assertPositive(quantity);
        this.setAvailable(this.available + quantity);
        return true;
    }

    /**
     * Freeze inventory
     */
    public boolean freeze(int quantity) {
        assertPositive(quantity);
        if (this.available >= quantity) {
            this.setAvailable(this.available - quantity);
            this.setWithheld(this.withheld + quantity);
            return true;
        }
        return false;
    }

    /**
     * Available + Withheld
     */
    public Integer getTotal() {
        return available + withheld;
    }

    /**
     * 扣减库存
     * 如果是预扣库存，则走release + reduce
     * 否则直接reduce
     */
    public boolean reduce(int quantity) {
        assertPositive(quantity);
        if (this.withheld > 0) {
            if (this.withheld >= quantity) {
                this.setWithheld(this.withheld - quantity);
                return true;
            }
        } else {
            if (this.available >= quantity) {
                this.setAvailable(this.available - quantity);
                return true;
            }
        }
        return false;
    }

    /**
     * Unfreeze withheld inventory
     */
    public boolean release(int quantity) {
        assertPositive(quantity);
        if (this.withheld >= quantity) {
            this.setWithheld(this.withheld - quantity);
            this.setAvailable(this.available + quantity);
            return true;
        }
        return false;
    }

    public boolean replenish(int quantity) {
        assertPositive(quantity);
        this.setAvailable(this.available + quantity);
        return true;
    }

    public void setAvailable(Integer newValue) {
        if (!newValue.equals(this.available)) {
            firePropertyChange("available", available, newValue);
            this.available = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setWithheld(Integer newValue) {
        if (!newValue.equals(this.withheld)) {
            firePropertyChange("withheld", withheld, newValue);
            this.withheld = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    private static void assertPositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("must be positive");
        }
    }
}
