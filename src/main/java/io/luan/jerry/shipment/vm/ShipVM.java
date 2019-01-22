package io.luan.jerry.shipment.vm;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShipVM implements Serializable {

    private Long shipmentId;

    private Long orderId;

    private String courier;

    private String trackingNumber;

    private String action;

}
