package io.luan.jerry.order.vm;

import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.order.domain.OrderAttributes;
import io.luan.jerry.order.domain.OrderLine;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderLineVM implements Serializable {

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

    public OrderLineVM() {
        //
    }

    public OrderLineVM(OrderLine orderLine) {
        this.sellerId = orderLine.getSellerId();
        this.itemId = orderLine.getItemId();
        var skuIdStr = orderLine.getAttribute(OrderAttributes.SKU_ID);
        if (skuIdStr != null) {
            this.skuId = Long.parseLong(skuIdStr);
        }
        this.itemPrice = orderLine.getItemPrice();
        this.itemTitle = orderLine.getItemTitle();
        this.itemImgUrl = orderLine.getItemImgUrl();
        this.quantity = orderLine.getQuantity();
        this.discountFee = orderLine.getDiscountFee();
        this.totalFee = orderLine.getTotalFee();
        this.attributes = MapUtils.encode(orderLine.getAttributes());
    }
}
