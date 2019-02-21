package io.luan.jerry.order.vm;

import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.order.domain.OrderAttributes;
import io.luan.jerry.order.domain.SubOrder;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubOrderVM implements Serializable {

    private Long sellerId;

    private Long itemId;

    private Long skuId;

    private Long itemPrice;

    private String itemTitle;

    private String itemImgUrl;

    private Long quantity;

    private Long discountFee;

    private Long totalFee;

    private String attributes;

    public SubOrderVM() {
        //
    }

    public SubOrderVM(SubOrder subOrder) {
        this.sellerId = subOrder.getSellerId();
        this.itemId = subOrder.getItemId();
        var skuIdStr = subOrder.getAttribute(OrderAttributes.SKU_ID);
        if (skuIdStr != null) {
            this.skuId = Long.parseLong(skuIdStr);
        }
        this.itemPrice = subOrder.getItemPrice();
        this.itemTitle = subOrder.getItemTitle();
        this.itemImgUrl = subOrder.getItemImgUrl();
        this.quantity = subOrder.getQuantity();
        this.discountFee = subOrder.getDiscountFee();
        this.totalFee = subOrder.getTotalFee();
        this.attributes = MapUtils.encode(subOrder.getAttributes());
    }
}
