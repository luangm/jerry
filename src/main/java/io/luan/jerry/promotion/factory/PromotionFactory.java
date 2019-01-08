package io.luan.jerry.promotion.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.promotion.data.PromotionDO;
import io.luan.jerry.promotion.domain.Promotion;
import io.luan.jerry.promotion.domain.PromotionType;
import org.springframework.stereotype.Component;

@Component
public class PromotionFactory {

    public Promotion loadFromData(PromotionDO promotionDO) {
        var promotion = new Promotion();
        promotion.setId(promotionDO.getId());
        promotion.setType(PromotionType.fromValue(promotionDO.getType()));
        promotion.setItemId(promotionDO.getItemId());
        promotion.setNewPrice(promotionDO.getNewPrice());
        promotion.setStartTime(promotionDO.getStartTime());
        promotion.setEndTime(promotionDO.getEndTime());
        promotion.setGmtCreate(promotionDO.getGmtCreate());
        promotion.setGmtModified(promotionDO.getGmtModified());
        promotion.setState(EntityState.Unchanged);
        return promotion;
    }
}
