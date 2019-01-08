package io.luan.jerry.order.data;

import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private Boolean isMain;
    private Boolean isSub;
    private Long buyerId;
    private Long sellerId;
    private Long itemId;
    private Integer quantity;
    private Long totalFee;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private List<OrderDO> subOrders = new ArrayList<>();

    public OrderDO() {
        //
    }

    private OrderDO(SubOrder subOrder) {
        this.id = subOrder.getId();
        this.isMain = false;
        this.isSub = true;
        this.parentId = 0L;
        this.itemId = subOrder.getItemId();
        this.buyerId = subOrder.getBuyerId();
        this.sellerId = subOrder.getSellerId();
        this.quantity = subOrder.getQuantity();
        this.totalFee = subOrder.getTotalFee();
        this.gmtCreate = subOrder.getGmtCreate();
        this.gmtModified = subOrder.getGmtModified();
    }

    public OrderDO(Order order) {

        this.id = order.getId();
        this.parentId = 0L;
        this.buyerId = order.getBuyerId();

        if (order.getSubOrders().size() == 1) {
            // Main + Sub
            var subOrder = order.getSubOrders().get(0);
            this.isMain = true;
            this.isSub = true;
            this.itemId = subOrder.getItemId();
            this.sellerId = subOrder.getSellerId();
            this.quantity = subOrder.getQuantity();
            this.totalFee = subOrder.getTotalFee();
            this.gmtCreate = subOrder.getGmtCreate();
            this.gmtModified = subOrder.getGmtModified();
        } else {
            // Main only
            this.isMain = true;
            this.isSub = false;
            this.itemId = 0L;
            this.sellerId = 0L;
            this.quantity = 0;
            this.totalFee = order.getTotalFee();
            this.subOrders = new ArrayList<>();
            this.gmtCreate = order.getGmtCreate();
            this.gmtModified = order.getGmtModified();
            for (var subOrder : order.getSubOrders()) {
                var subOrderDO = new OrderDO(subOrder);
                this.subOrders.add(subOrderDO);
            }
        }

    }
}
