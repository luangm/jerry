package io.luan.jerry.order.data;

import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private Integer quantity; // sub + main
    private Long discountFee; // sub
    private Long totalFee; // main
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private List<OrderDO> subOrders = new ArrayList<>();
    private Integer status;
    private Integer payStatus;
    private Integer shipStatus;

    public OrderDO() {
        //
    }

    private OrderDO(SubOrder subOrder) {
        this.id = subOrder.getId();
        this.isMain = false;
        this.isSub = true;
        this.parentId = 0L;
        this.buyerId = subOrder.getBuyerId();
        this.sellerId = subOrder.getSellerId();

        this.itemId = subOrder.getItemId();
        this.itemPrice = subOrder.getItemPrice();
        this.itemTitle = subOrder.getItemTitle();
        this.itemImgUrl = subOrder.getItemImgUrl();

        this.quantity = subOrder.getQuantity();
        this.discountFee = subOrder.getDiscountFee();
        this.totalFee = 0L;

        this.gmtCreate = subOrder.getGmtCreate();
        this.gmtModified = subOrder.getGmtModified();

        this.status = subOrder.getStatus().getValue();
        this.payStatus = subOrder.getPayStatus().getValue();
        this.shipStatus = subOrder.getShipStatus().getValue();
    }

    public OrderDO(Order order) {

        this.id = order.getId();
        this.parentId = order.getId() != null ? order.getId() : 0L;
        this.buyerId = order.getBuyerId();
        this.sellerId = order.getSellerId();
        this.status = order.getStatus().getValue();
        this.payStatus = order.getPayStatus().getValue();
        this.shipStatus = order.getShipStatus().getValue();

        if (order.getSubOrders().size() == 1) {
            // Main + Sub
            var subOrder = order.getSubOrders().get(0);
            this.isMain = true;
            this.isSub = true;
            this.itemId = subOrder.getItemId();
            this.itemPrice = subOrder.getItemPrice();
            this.itemTitle = subOrder.getItemTitle();
            this.itemImgUrl = subOrder.getItemImgUrl();
            this.discountFee = subOrder.getDiscountFee();
            this.quantity = order.getQuantity();
            this.totalFee = order.getTotalFee();
            this.gmtCreate = order.getGmtCreate();
            this.gmtModified = order.getGmtModified();
        } else {
            // Main only
            this.isMain = true;
            this.isSub = false;
            this.itemId = 0L;
            this.itemPrice = 0L;
            this.itemTitle = null;
            this.itemImgUrl = null;
            this.quantity = 0;
            this.discountFee = 0L;
            this.totalFee = order.getTotalFee();
            this.gmtCreate = order.getGmtCreate();
            this.gmtModified = order.getGmtModified();
            this.subOrders = order.getSubOrders().stream().map(OrderDO::new).collect(Collectors.toList());
        }

    }
}
