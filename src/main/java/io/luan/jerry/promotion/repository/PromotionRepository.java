package io.luan.jerry.promotion.repository;

import io.luan.jerry.common.repository.Repository;
import io.luan.jerry.promotion.domain.Promotion;

import java.util.List;

public interface PromotionRepository extends Repository<Promotion, Long> {

    List<Promotion> findByItemId(Long itemId);

    List<Promotion> findByItemIds(List<Long> itemIds);
}
