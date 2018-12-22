package io.luan.jerry.item.repository.impl;

import io.luan.jerry.item.data.ItemDO;
import io.luan.jerry.item.data.ItemMapper;
import io.luan.jerry.item.domain.Item;
import io.luan.jerry.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemMapper itemMapper;

    @Autowired
    public ItemRepositoryImpl(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public boolean delete(Item item) {
        var itemDO = ItemDO.fromEntity(item);
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
            Item item = itemDO.toEntity();
            items.add(item);
        }
        return items;
    }

    @Override
    public Item findById(Long id) {
        var itemDO = itemMapper.findById(id);
        if (itemDO != null) {
            return itemDO.toEntity();
        }
        return null;
    }

    @Override
    public Item save(Item item) {
        var itemDO = ItemDO.fromEntity(item);
        if (itemDO.getId() == null) {
            itemMapper.insert(itemDO);
            return findById(itemDO.getId());
        } else {
            return item;
        }
    }

}
