package io.luan.jerry.item.service;

import io.luan.jerry.item.domain.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findById(Long id);

    Item publish(Item item);
}
