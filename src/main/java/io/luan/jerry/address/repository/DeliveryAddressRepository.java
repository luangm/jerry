package io.luan.jerry.address.repository;

import io.luan.jerry.address.domain.DeliveryAddress;
import io.luan.jerry.common.repository.Repository;

import java.util.List;

public interface DeliveryAddressRepository extends Repository<DeliveryAddress, Long> {

    List<DeliveryAddress> findAllByUserId(Long userId);

    DeliveryAddress findDefaultByUserId(Long userId);
}
