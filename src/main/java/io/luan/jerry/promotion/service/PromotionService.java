package io.luan.jerry.promotion.service;

import io.luan.jerry.promotion.domain.Promotion;
import io.luan.jerry.promotion.dto.PublishPromotionDTO;

import java.util.List;
import java.util.Map;

public interface PromotionService {

    Promotion findById(Long id);

    List<Promotion> findByItemId(Long itemId);

    Map<Long, Promotion> findByItemIds(List<Long> itemIds);

    Promotion publish(PublishPromotionDTO request);

    Promotion save(Promotion promotion);
}
