package io.luan.jerry.shipment.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.shipment.data.ShipmentDO;
import io.luan.jerry.shipment.domain.Shipment;
import io.luan.jerry.shipment.domain.ShipmentMethod;
import io.luan.jerry.shipment.domain.ShipmentStatus;
import org.springframework.stereotype.Component;

@Component
public class ShipmentFactory {

    public Shipment load(ShipmentDO shipmentDO) {
        var shipment = new Shipment();
        shipment.setId(shipmentDO.getId());
        shipment.setOrderId(shipmentDO.getOrderId());
        shipment.setSellerId(shipmentDO.getSellerId());
        shipment.setBuyerId(shipmentDO.getBuyerId());
        shipment.setMethod(ShipmentMethod.fromValue(shipmentDO.getMethod()));
        shipment.setAddress(shipmentDO.getAddress());
        shipment.setGmtCreate(shipmentDO.getGmtCreate());
        shipment.setGmtModified(shipmentDO.getGmtModified());
        shipment.setStatus(ShipmentStatus.fromValue(shipmentDO.getStatus()));
        shipment.setState(EntityState.Unchanged);
        return shipment;
    }
}
