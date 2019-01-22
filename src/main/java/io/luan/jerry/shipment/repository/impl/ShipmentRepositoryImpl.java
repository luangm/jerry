package io.luan.jerry.shipment.repository.impl;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.shipment.data.ShipmentDO;
import io.luan.jerry.shipment.data.ShipmentMapper;
import io.luan.jerry.shipment.domain.Shipment;
import io.luan.jerry.shipment.factory.ShipmentFactory;
import io.luan.jerry.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final ShipmentFactory shipmentFactory;

    private final ShipmentMapper shipmentMapper;

    @Autowired
    public ShipmentRepositoryImpl(ShipmentFactory shipmentFactory, ShipmentMapper shipmentMapper) {
        this.shipmentFactory = shipmentFactory;
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public boolean delete(Shipment shipment) {
        var shipmentDO = new ShipmentDO(shipment);
        if (shipmentDO.getId() != null) {
            int count = shipmentMapper.delete(shipmentDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Shipment> findAll() {
        List<Shipment> shipments = new ArrayList<>();
        for (ShipmentDO shipmentDO : shipmentMapper.findAll()) {
            shipments.add(shipmentFactory.load(shipmentDO));
        }
        return shipments;
    }

    @Override
    public Shipment findById(Long id) {
        var shipmentDO = shipmentMapper.findById(id);
        return shipmentFactory.load(shipmentDO);
    }

    @Override
    public void save(Shipment shipment) {
        var shipmentDO = new ShipmentDO(shipment);
        switch (shipment.getState()) {
            case Added:
            case Detached:
                shipmentMapper.insert(shipmentDO);
                shipment.setId(shipmentDO.getId());
                break;
            case Modified:
                shipmentMapper.update(shipmentDO);
                break;
        }
        shipment.setState(EntityState.Unchanged);
    }

    @Override
    public Shipment findByOrderId(Long orderId) {
        var shipmentDO = shipmentMapper.findByOrderId(orderId);
        return shipmentFactory.load(shipmentDO);
    }
}
