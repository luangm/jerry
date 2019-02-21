package io.luan.jerry.sell.service;

import io.luan.jerry.item.domain.Item;
import io.luan.jerry.sell.dto.PublishItemRequest;
import io.luan.jerry.user.domain.User;

public interface SellService {

    Item publish(User user, PublishItemRequest request);

    Item edit(User user, PublishItemRequest request);
}
