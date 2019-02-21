package io.luan.jerry.order.data;

import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderLine;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private Boolean isMain;
    private Boolean isSub;
    private Long buyerId;
    private Long sellerId;
    private Long itemId; // sub
    private Long itemPrice; // sub
    private String itemTitle; // sub, nullable
    private String itemImgUrl; // sub, nullable
    private Long quantity; // sub + main
    private Long discountFee; // sub
    private Long totalFee; // main
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;
    private List<OrderDO> orderLines = new ArrayList<>();
    private Integer status;
    private Integer payStatus;
    private Integer shipStatus;
    private String attributes;

    public OrderDO() {
        //
    }

    private OrderDO(OrderLine orderLine) {
        this.id = orderLine.getId();
        this.isMain = false;
        this.isSub = true;
        this.parentId = 0L;
        this.buyerId = orderLine.getBuyerId();
        this.sellerId = orderLine.getSellerId();

        this.itemId = orderLine.getItemId();
        this.itemPrice = orderLine.getItemPrice();
        this.itemTitle = orderLine.getItemTitle();
        this.itemImgUrl = orderLine.getItemImgUrl();

        this.quantity = orderLine.getQuantity();
        this.discountFee = orderLine.getDiscountFee();
        this.totalFee = 0L;

        this.gmtCreate = orderLine.getGmtCreate();
        this.gmtModified = orderLine.getGmtModified();

        this.status = orderLine.getStatus().getValue();
        this.payStatus = orderLine.getPayStatus().getValue();
        this.shipStatus = orderLine.getShipStatus().getValue();

        this.attributes = MapUtils.encode(orderLine.getAttributes());
    }

    public OrderDO(Order order) {

        this.id = order.getId();
        this.parentId = order.getId() != null ? order.getId() : 0L;
        this.buyerId = order.getBuyerId();
        this.sellerId = order.getSellerId();
        this.status = order.getStatus().getValue();
        this.payStatus = order.getPayStatus().getValue();
        this.shipStatus = order.getShipStatus().getValue();
        this.attributes = MapUtils.encode(order.getAttributes());

        if (order.getOrderLines().size() == 1) {
            // Main + Sub
            var orderLine = order.getOrderLines().get(0);
            this.isMain = true;
            this.isSub = true;
            this.itemId = orderLine.getItemId();
            this.itemPrice = orderLine.getItemPrice();
            this.itemTitle = orderLine.getItemTitle();
            this.itemImgUrl = orderLine.getItemImgUrl();
            this.discountFee = orderLine.getDiscountFee();
            this.quantity = order.getQuantity();
            this.totalFee = order.getTotalFee();
            this.gmtCreate = order.getGmtCreate();
            this.gmtModified = order.getGmtModified();
            this.attributes = MapUtils.encode(orderLine.getAttributes());

        } else {
            // Main only
            this.isMain = true;
            this.isSub = false;
            this.itemId = 0L;
            this.itemPrice = 0L;
            this.itemTitle = null;
            this.itemImgUrl = null;
            this.quantity = 0L;
            this.discountFee = 0L;
            this.totalFee = order.getTotalFee();
            this.gmtCreate = order.getGmtCreate();
            this.gmtModified = order.getGmtModified();
            this.orderLines = order.getOrderLines().stream().map(OrderDO::new).collect(Collectors.toList());
        }

    }
}
