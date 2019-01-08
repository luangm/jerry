package io.luan.jerry.promotion.service.impl;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.promotion.domain.Promotion;
import io.luan.jerry.promotion.domain.PromotionType;
import io.luan.jerry.promotion.dto.PublishPromotionDTO;
import io.luan.jerry.promotion.repository.PromotionRepository;
import io.luan.jerry.promotion.service.PromotionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log
public class PromotionServiceImpl implements PromotionService {

    private final ItemService itemService;

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ItemService itemService) {
        this.promotionRepository = promotionRepository;
        this.itemService = itemService;
    }

    @Override
    public Promotion findById(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    public List<Promotion> findByItemId(Long itemId) {
        return promotionRepository.findByItemId(itemId);
    }

    @Override
    public Map<Long, Promotion> findByItemIds(List<Long> itemIds) {
        var list = promotionRepository.findByItemIds(itemIds);
        Map<Long, Promotion> map = new HashMap<>();
        for (var item : list) {
            var existing = map.get(item.getItemId());
            if (existing != null) {
                if (item.getNewPrice() < existing.getNewPrice()) {
                    map.put(item.getItemId(), item);
                }
            } else {
                map.put(item.getItemId(), item);
            }
        }
        return map;
    }

    @Override
    public Promotion publish(PublishPromotionDTO request) {
        // Check item's new price is less than existing
        var item = itemService.findById(request.getItemId());
        if (request.getNewPrice() >= item.getPrice()) {
            throw new IllegalArgumentException("New Price must be less than existing price");
        }

        Promotion promotion = new Promotion();
        promotion.setType(PromotionType.SingleItem);
        promotion.setItemId(request.getItemId());
        promotion.setNewPrice(request.getNewPrice());
        promotion.setStartTime(request.getStartTime());
        promotion.setEndTime(request.getEndTime());
        promotionRepository.save(promotion);

        return promotion;
    }

    @Override
    public Promotion save(Promotion promotion) {
        promotionRepository.save(promotion);
        return promotion;
    }
}
