package io.luan.jerry.shipment.service.impl;

import io.luan.jerry.shipment.domain.Shipment;
import io.luan.jerry.shipment.repository.ShipmentRepository;
import io.luan.jerry.shipment.service.ShipmentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment findById(Long id) {
        return shipmentRepository.findById(id);
    }

    @Override
    public Shipment findByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }

    @Override
    public Shipment save(Shipment shipment) {
        shipmentRepository.save(shipment);
        return shipment;
    }

}
