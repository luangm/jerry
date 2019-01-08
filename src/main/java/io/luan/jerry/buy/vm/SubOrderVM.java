package io.luan.jerry.buy.vm;

import io.luan.jerry.order.domain.SubOrder;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubOrderVM implements Serializable {

    private Long sellerId;

    private Long itemId;

    private Integer quantity;

    private Long totalFee;

    public SubOrderVM() {
        //
    }

    public SubOrderVM(SubOrder subOrder) {
        this.sellerId = subOrder.getSellerId();
        this.itemId = subOrder.getItemId();
        this.quantity = subOrder.getQuantity();
        this.totalFee = subOrder.getTotalFee();
    }
}
