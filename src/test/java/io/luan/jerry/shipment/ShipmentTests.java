package io.luan.jerry.shipment;

import io.luan.jerry.address.domain.Address;
import io.luan.jerry.shipment.data.ShipmentMapper;
import io.luan.jerry.shipment.domain.Shipment;
import io.luan.jerry.shipment.domain.ShipmentMethod;
import io.luan.jerry.shipment.repository.ShipmentRepository;
import io.luan.jerry.shipment.service.ShipmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShipmentTests {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    @Test
    public void repoSave() {
        Long orderId = System.currentTimeMillis() / 1000;
        var shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setBuyerId(222L);
        shipment.setSellerId(333L);
        shipment.setAddress(new Address("Address 123"));
        shipment.setMethod(ShipmentMethod.Courier);

        shipmentService.save(shipment);
    }

}
