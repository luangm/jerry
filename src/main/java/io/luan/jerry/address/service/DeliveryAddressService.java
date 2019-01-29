package io.luan.jerry.address.service;

import io.luan.jerry.address.domain.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {

    boolean delete(DeliveryAddress deliveryAddress);

    List<DeliveryAddress> findAllByUserId(Long userId);

    DeliveryAddress findById(Long id);

    DeliveryAddress findDefaultByUserId(Long userId);

    DeliveryAddress save(DeliveryAddress deliveryAddress);
}
