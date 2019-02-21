package io.luan.jerry.inventory.service.impl;

import io.luan.jerry.inventory.domain.Inventory;
import io.luan.jerry.inventory.repository.InventoryRepository;
import io.luan.jerry.inventory.service.InventoryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> findAllByItemId(Long itemId) {
        return inventoryRepository.findAllByItemId(itemId);
    }

    @Override
    public Inventory findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public Inventory save(Inventory inventory) {
        inventoryRepository.save(inventory);
        return inventory;
    }

    @Override
    public boolean freezeBatch(List<Long> ids, List<Long> quantities) {
        var inventories = inventoryRepository.findBatch(ids);
        for (int i = 0; i < inventories.size(); i++) {
            var inv = inventories.get(i);
            var quantity = quantities.get(i);
            var success = inv.freeze(quantity);
            if (!success) {
                return false;
            }
        }

        inventoryRepository.saveBatch(inventories);

        return true;
    }

    @Override
    public boolean reduceBatch(List<Long> ids, List<Long> quantities) {
        var inventories = inventoryRepository.findBatch(ids);
        for (int i = 0; i < inventories.size(); i++) {
            var inv = inventories.get(i);
            var quantity = quantities.get(i);
            var success = inv.reduce(quantity);
            if (!success) {
                return false;
            }
        }

        inventoryRepository.saveBatch(inventories);

        return true;
    }


}
