package io.luan.jerry.promotion.repository.impl;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.promotion.data.PromotionDO;
import io.luan.jerry.promotion.data.PromotionMapper;
import io.luan.jerry.promotion.domain.Promotion;
import io.luan.jerry.promotion.factory.PromotionFactory;
import io.luan.jerry.promotion.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PromotionRepositoryImpl implements PromotionRepository {

    private final PromotionMapper promotionMapper;
    
    private final PromotionFactory promotionFactory;

    @Autowired
    public PromotionRepositoryImpl(PromotionMapper promotionMapper, PromotionFactory promotionFactory) {
        this.promotionMapper = promotionMapper;
        this.promotionFactory = promotionFactory;
    }

    @Override
    public boolean delete(Promotion promotion) {
        var promotionDO = new PromotionDO(promotion);
        if (promotionDO.getId() != null) {
            int count = promotionMapper.delete(promotionDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Promotion> findAll() {
        List<Promotion> list = new ArrayList<>();
        for (PromotionDO promotionDO : promotionMapper.findAll()) {
            Promotion promotion = promotionFactory.load(promotionDO);
            list.add(promotion);
        }
        return list;
    }

    @Override
    public Promotion findById(Long id) {
        var promotionDO = promotionMapper.findById(id);
        if (promotionDO != null) {
            return promotionFactory.load(promotionDO);
        }
        return null;
    }

    @Override
    public List<Promotion> findByItemId(Long itemId) {
        List<Promotion> list = new ArrayList<>();
        for (PromotionDO promotionDO : promotionMapper.findAllByItemIdAndTime(itemId, LocalDateTime.now())) {
            Promotion promotion = promotionFactory.load(promotionDO);
            list.add(promotion);
        }
        return list;
    }

    @Override
    public List<Promotion> findByItemIds(List<Long> itemIds) {
        List<Promotion> list = new ArrayList<>();
        for (PromotionDO promotionDO : promotionMapper.findByItemIds(itemIds, LocalDateTime.now())) {
            Promotion promotion = promotionFactory.load(promotionDO);
            list.add(promotion);
        }
        return list;
    }

    @Override
    @Transactional
    public void save(Promotion promotion) {
        var promotionDO = new PromotionDO(promotion);
        switch (promotion.getState()) {
            case Added:
            case Detached:
                promotionMapper.insert(promotionDO);
                promotion.setId(promotionDO.getId());
                break;
            case Modified:
                promotionMapper.update(promotionDO);
                break;
        }
        promotion.setState(EntityState.Unchanged);
    }

}
