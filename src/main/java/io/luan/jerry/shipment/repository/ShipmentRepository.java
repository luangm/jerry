package io.luan.jerry.shipment.repository;

import io.luan.jerry.common.repository.Repository;
import io.luan.jerry.shipment.domain.Shipment;

public interface ShipmentRepository extends Repository<Shipment, Long> {

    Shipment findByOrderId(Long orderId);

}
