package io.luan.jerry.inventory.repository.impl;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.common.repository.VersionConflictException;
import io.luan.jerry.inventory.data.InventoryDO;
import io.luan.jerry.inventory.data.InventoryMapper;
import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.factory.InventoryFactory;
import io.luan.jerry.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryFactory factory;

    private final InventoryMapper mapper;

    @Autowired
    public InventoryRepositoryImpl(InventoryFactory factory, InventoryMapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    @Override
    public boolean delete(Inventory inventory) {
        var inventoryDO = new InventoryDO(inventory);
        if (inventoryDO.getId() != null) {
            int count = mapper.delete(inventoryDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Inventory> findAll() {
        List<Inventory> inventories = new ArrayList<>();
        for (InventoryDO inventoryDO : mapper.findAll()) {
            inventories.add(factory.load(inventoryDO));
        }
        return inventories;
    }

    @Override
    public Inventory findById(Long id) {
        var inventoryDO = mapper.findById(id);
        if (inventoryDO != null) {
            return factory.load(inventoryDO);
        }
        return null;
    }

    @Override
    public void save(Inventory inventory) {
        var inventoryDO = new InventoryDO(inventory);
        switch (inventory.getState()) {
            case Added:
            case Detached:
                mapper.insert(inventoryDO);
                inventory.setId(inventoryDO.getId());
                inventory.setVersion(1L);
                break;
            case Modified:
                int success = mapper.update(inventoryDO);
                if (success != 1) {
                    throw new VersionConflictException();
                }
                inventory.setVersion(inventory.getVersion() + 1);
                break;
        }
        inventory.setState(EntityState.Unchanged);
    }

    @Override
    public List<Inventory> findAllByItemId(Long itemId) {
        List<Inventory> inventories = new ArrayList<>();
        for (InventoryDO inventoryDO : mapper.findAllByItemId(itemId)) {
            inventories.add(factory.load(inventoryDO));
        }
        return inventories;
    }

    @Override
    public List<Inventory> findBatch(List<Long> ids) {
        Map<Long, Inventory> map = new HashMap<>();
        var items = mapper.findBatch(ids);
        for (InventoryDO inventoryDO : mapper.findBatch(ids)) {
            map.put(inventoryDO.getId(), factory.load(inventoryDO));
        }
        return ids.stream().map(map::get).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveBatch(List<Inventory> list) {
        for (var item : list) {
            save(item);
        }
    }
}
