package io.luan.jerry.detail.service;

import io.luan.jerry.detail.dto.ItemDetail;

public interface DetailService {

    ItemDetail getDetail(Long itemId, Long userId);

}
