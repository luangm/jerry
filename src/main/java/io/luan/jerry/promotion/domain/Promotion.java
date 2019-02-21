package io.luan.jerry.promotion.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Promotion extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Promotion ID
     */
    private Long id;

    /**
     * Type of promotion
     */
    private PromotionType type = PromotionType.SingleItem;

    /**
     * Item ID
     */
    private Long itemId;

    /**
     * Sku ID
     */
    private Long skuId;

    /**
     * New Price of item
     */
    private Long newPrice;

    /**
     * Start of Promotion
     */
    private LocalDateTime startTime;

    /**
     * End of promotion
     */
    private LocalDateTime endTime;

    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

    public void setEndTime(LocalDateTime newValue) {
        if (!newValue.equals(this.endTime)) {
            firePropertyChange("endTime", endTime, newValue);
            this.endTime = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }

    public void setNewPrice(Long newValue) {
        if (!newValue.equals(this.newPrice)) {
            firePropertyChange("newPrice", newPrice, newValue);
            this.newPrice = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }

    public void setStartTime(LocalDateTime newValue) {
        if (!newValue.equals(this.startTime)) {
            firePropertyChange("startTime", startTime, newValue);
            this.startTime = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }
}