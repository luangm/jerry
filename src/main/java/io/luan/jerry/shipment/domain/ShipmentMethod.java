package io.luan.jerry.shipment.domain;

import lombok.Getter;

public enum ShipmentMethod {

    Other(0),
    Mail(1),
    Courier(2),
    Freight(3),
    Virtual(4),
    Offline(5);

    @Getter
    private int value;

    ShipmentMethod(int value) {
        this.value = value;
    }

    public static ShipmentMethod fromValue(int value) {
        switch (value) {
            case 1:
                return Mail;
            case 2:
                return Courier;
            case 3:
                return Freight;
            case 4:
                return Virtual;
            case 5:
                return Offline;
            default:
                return Other;
        }
    }
}
