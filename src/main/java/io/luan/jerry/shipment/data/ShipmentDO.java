package io.luan.jerry.shipment.data;

import io.luan.jerry.shipment.domain.Shipment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ShipmentDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private Long buyerId;
    private Long sellerId;
    private Integer method;
    private String address;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Integer status;

    public ShipmentDO() {
        //
    }

    public ShipmentDO(Shipment shipment) {
        this.id = shipment.getId();
        this.orderId = shipment.getOrderId();
        this.buyerId = shipment.getBuyerId();
        this.sellerId = shipment.getSellerId();
        this.method = shipment.getMethod().getValue();
        this.address = shipment.getAddress();
        this.gmtCreate = shipment.getGmtCreate();
        this.gmtModified = shipment.getGmtModified();
        this.status = shipment.getStatus().getValue();
    }
}
