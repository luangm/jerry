package io.luan.jerry.shipment.domain;

import lombok.Getter;

public enum ShipmentState {

    Created(0),
    Shipped(1),
    Received(2),
    Returned(3),
    Canceled(-1),
    Exception(-2);

    @Getter
    private int value;

    ShipmentState(int value) {
        this.value = value;
    }

    public static ShipmentState fromValue(int value) {
        switch (value) {
            case 0:
                return Created;
            case 1:
                return Shipped;
            case 2:
                return Received;
            case 3:
                return Returned;
            case -1:
                return Canceled;
            default:
                return Exception;
        }
    }
}
