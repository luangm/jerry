package io.luan.jerry.buy.vm;

import io.luan.jerry.buy.dto.ConfirmOrderResult;
import io.luan.jerry.order.vm.SubOrderVM;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConfirmOrderVM implements Serializable {

    private Long orderId;

    private Long buyerId;

    private Long totalFee;

    private Integer quantity;

    private String address;

    private List<SubOrderVM> subOrders = new ArrayList<>();

    public ConfirmOrderVM() {
        //
    }

    public ConfirmOrderVM(ConfirmOrderResult result) {
        var order = result.getOrder();

        this.orderId = order.getId();
        this.buyerId = order.getBuyerId();
        for (var subOrder : order.getSubOrders()) {
            subOrders.add(new SubOrderVM(subOrder));
        }
        this.totalFee = order.getTotalFee();
        this.quantity = order.getQuantity();

        var address = result.getShipment().getAddress();
        if (address != null) {
            this.address = address.getAddress();
        }
    }
}
