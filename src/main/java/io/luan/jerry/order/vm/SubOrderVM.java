package io.luan.jerry.order.vm;

import io.luan.jerry.order.domain.SubOrder;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubOrderVM implements Serializable {

    private Long sellerId;

    private Long itemId;

    private Long itemPrice;

    private String itemTitle;

    private String itemImgUrl;

    private Long quantity;

    private Long discountFee;

    private Long totalFee;

    public SubOrderVM() {
        //
    }

    public SubOrderVM(SubOrder subOrder) {
        this.sellerId = subOrder.getSellerId();
        this.itemId = subOrder.getItemId();
        this.itemPrice = subOrder.getItemPrice();
        this.itemTitle = subOrder.getItemTitle();
        this.itemImgUrl = subOrder.getItemImgUrl();
        this.quantity = subOrder.getQuantity();
        this.discountFee = subOrder.getDiscountFee();
        this.totalFee = subOrder.getTotalFee();
    }
}
