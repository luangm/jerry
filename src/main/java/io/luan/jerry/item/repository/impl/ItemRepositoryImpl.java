package io.luan.jerry.item.repository.impl;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.item.data.ItemDO;
import io.luan.jerry.item.data.ItemMapper;
import io.luan.jerry.item.domain.Item;
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

    @Autowired
    public ItemRepositoryImpl(ItemMapper itemMapper, ItemFactory itemFactory) {
        this.itemMapper = itemMapper;
        this.itemFactory = itemFactory;
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
        var itemDO = new ItemDO(item);
        switch (item.getState()) {
            case Added:
            case Detached:
                itemMapper.insert(itemDO);
                item.setId(itemDO.getId());
                break;
            case Modified:
                itemMapper.update(itemDO);
                break;
        }
        item.setState(EntityState.Unchanged);
    }

}
