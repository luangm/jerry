package io.luan.jerry.inventory.service;

import io.luan.jerry.inventory.domain.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory findById(Long id);

    Inventory save(Inventory inventory);

    boolean freezeBatch(List<Long> ids, List<Long> quantities);

    boolean reduceBatch(List<Long> ids, List<Long> quantities);
}
