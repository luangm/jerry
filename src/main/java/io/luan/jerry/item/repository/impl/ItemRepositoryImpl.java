package io.luan.jerry.item.repository.impl;

import io.luan.jerry.common.repository.RepositoryHelper;
import io.luan.jerry.item.data.ItemDO;
import io.luan.jerry.item.data.ItemMapper;
import io.luan.jerry.item.data.SkuDO;
import io.luan.jerry.item.data.SkuMapper;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.domain.Sku;
import io.luan.jerry.item.factory.ItemFactory;
import io.luan.jerry.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemMapper itemMapper;
    private final ItemFactory itemFactory;
    private final SkuMapper skuMapper;

    @Autowired
    public ItemRepositoryImpl(ItemMapper itemMapper, ItemFactory itemFactory, SkuMapper skuMapper) {
        this.itemMapper = itemMapper;
        this.itemFactory = itemFactory;
        this.skuMapper = skuMapper;
    }

    @Override
    public boolean delete(Item item) {
        var itemDO = new ItemDO(item);
        if (itemDO.getId() != null) {
            int count = itemMapper.delete(itemDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        for (ItemDO itemDO : itemMapper.findAll()) {
            Item item = itemFactory.load(itemDO);
            items.add(item);
        }
        return items;
    }

    @Override
    public Item findById(Long id) {
        var itemDO = itemMapper.findById(id);
        if (itemDO != null) {
            return itemFactory.load(itemDO);
        }
        return null;
    }

    @Override
    @Transactional
    public void save(Item item) {
        RepositoryHelper.save(item, this::insert, this::update);
        item.getSkus().forEach(this::save);
    }

    @Override
    public Item findById(Long id, boolean withSku) {
        if (!withSku) {
            return findById(id);
        }

        var itemDO = itemMapper.findById(id);
        if (itemDO != null) {
            var skuList = skuMapper.findAllByItemId(id);
            return itemFactory.load(itemDO, skuList);
        }
        return null;
    }

    private void insert(Item item) {
        var itemDO = new ItemDO(item);
        itemMapper.insert(itemDO);
        item.setId(itemDO.getId());
    }

    private void insert(Sku sku) {
        var skuDO = new SkuDO(sku);
        skuMapper.insert(skuDO);
        sku.setId(skuDO.getId());
    }

    private void save(Sku sku) {
        RepositoryHelper.save(sku, this::insert, this::update);
    }

    private void update(Sku sku) {
        var skuDO = new SkuDO(sku);
        skuMapper.update(skuDO);
    }

    private void update(Item item) {
        var itemDO = new ItemDO(item);
        itemMapper.update(itemDO);
    }

}
