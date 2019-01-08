package io.luan.jerry.promotion.vm;

import io.luan.jerry.promotion.domain.Promotion;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromotionVM implements Serializable {

    private Long id;
    private Long itemId;
    private Long newPrice;
    private String startTime;
    private String endTime;

    public PromotionVM() {
        //
    }

    public PromotionVM(Promotion promotion) {
        this.id = promotion.getId();
        this.itemId = promotion.getItemId();
        this.newPrice = promotion.getNewPrice();
        this.startTime = promotion.getStartTime().toString();
        this.endTime = promotion.getEndTime().toString();
    }
}
