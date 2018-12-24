package io.luan.jerry.item.service;

import io.luan.jerry.item.domain.Item;

import java.util.List;

public interface ItemService {

    Item create(String title, Long price);

    List<Item> findAll();

    Item findById(Long id);
}
