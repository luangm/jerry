package io.luan.jerry.promotion.data;

import io.luan.jerry.promotion.domain.Promotion;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromotionDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Integer type;
    private Long itemId;
    private Long newPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public PromotionDO() {
        //
    }

    public PromotionDO(Promotion promotion) {
        this.id = promotion.getId();
        this.type = promotion.getType().getValue();
        this.itemId = promotion.getItemId();
        this.newPrice = promotion.getNewPrice();
        this.startTime = promotion.getStartTime();
        this.endTime = promotion.getEndTime();
        this.gmtCreate = promotion.getGmtCreate();
        this.gmtModified = promotion.getGmtModified();
    }
}
