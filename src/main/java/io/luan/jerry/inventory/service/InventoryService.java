package io.luan.jerry.inventory.service;

import io.luan.jerry.inventory.domain.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> findAllByItemId(Long itemId);

    Inventory findById(Long id);

    boolean freezeBatch(List<Long> ids, List<Long> quantities);

    boolean reduceBatch(List<Long> ids, List<Long> quantities);

    Inventory save(Inventory inventory);
}
