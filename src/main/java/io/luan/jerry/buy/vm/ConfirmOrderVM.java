package io.luan.jerry.buy.vm;

import io.luan.jerry.buy.dto.ConfirmOrderResult;
import io.luan.jerry.order.vm.OrderLineVM;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConfirmOrderVM implements Serializable {

    private Long orderId;

    private Long buyerId;

    private Long totalFee;

    private Long quantity;

    private String address;

    private List<OrderLineVM> orderLines = new ArrayList<>();

    public ConfirmOrderVM() {
        //
    }

    public ConfirmOrderVM(ConfirmOrderResult result) {
        var order = result.getOrder();

        this.orderId = order.getId();
        this.buyerId = order.getBuyerId();
        for (var orderLine : order.getOrderLines()) {
            orderLines.add(new OrderLineVM(orderLine));
        }
        this.totalFee = order.getTotalFee();
        this.quantity = order.getQuantity();

        var address = result.getShipment().getAddress();
        if (address != null) {
            this.address = address.getAddress();
        }
    }
}
