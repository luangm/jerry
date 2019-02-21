package io.luan.jerry.inventory.repository;

import io.luan.jerry.common.repository.Repository;
import io.luan.jerry.inventory.domain.Inventory;

import java.util.List;

public interface InventoryRepository extends Repository<Inventory, Long> {

    List<Inventory> findAllByItemId(Long itemId);

    List<Inventory> findBatch(List<Long> ids);

    void saveBatch(List<Inventory> list);
}
