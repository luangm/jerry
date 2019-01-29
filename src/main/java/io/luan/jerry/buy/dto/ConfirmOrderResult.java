package io.luan.jerry.buy.dto;

import io.luan.jerry.order.domain.Order;
import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.shipment.domain.Shipment;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmOrderResult implements Serializable {

    private Order order;
    private Payment payment;
    private Shipment shipment;

}
