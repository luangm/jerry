package io.luan.jerry;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class ItemService {

    private final ItemMapper itemMapper;

    @Autowired
    public ItemService(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public Item create(String title, long price) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemMapper.insert(item);

        return getById(item.getId());
    }

    public List<Item> getAll() {
        return itemMapper.findAll();
    }

    public Item getById(long id) {
        return itemMapper.findById(id);
    }

}
