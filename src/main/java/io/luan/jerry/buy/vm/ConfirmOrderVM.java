package io.luan.jerry.buy.vm;

import io.luan.jerry.order.domain.Order;
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

    public ConfirmOrderVM(Order entity) {
        this.orderId = entity.getId();
        this.buyerId = entity.getBuyerId();
        for (var subOrder : entity.getSubOrders()) {
            subOrders.add(new SubOrderVM(subOrder));
        }
        this.totalFee = entity.getTotalFee();
        this.quantity = entity.getQuantity();
    }
}
