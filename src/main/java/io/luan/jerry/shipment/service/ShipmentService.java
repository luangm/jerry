package io.luan.jerry.shipment.service;

import io.luan.jerry.shipment.domain.Shipment;

public interface ShipmentService {

    Shipment findById(Long id);

    Shipment findByOrderId(Long orderId);

    Shipment save(Shipment shipment);

}
