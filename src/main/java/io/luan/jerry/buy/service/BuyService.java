package io.luan.jerry.buy.service;

import io.luan.jerry.buy.dto.ConfirmOrderResult;
import io.luan.jerry.buy.dto.OrderDTO;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.payment.vm.PayVM;
import io.luan.jerry.shipment.vm.ShipVM;

public interface BuyService {

    /**
     * Using the request, create an Order and Save to db
     */
    Order createOrder(OrderDTO request);

    /**
     * Using the request, create an Order Entity, but not save it
     */
    ConfirmOrderResult confirmOrder(OrderDTO request);

    /**
     * Pay for the order.
     * Update Payment
     * Update Order.PayStatus
     */
    Order payOrder(PayVM request);

    /**
     * Receive Order
     */
    Order receiveOrder(ShipVM request);

    /**
     * Ship Order
     *
     * Update Shipment
     * Update Order.ShipStatus
     */
    Order shipOrder(ShipVM request);
}
