package io.luan.jerry.order.data;

import io.luan.jerry.order.domain.Order;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long buyerId;
    private Long sellerId;
    private Long itemId;
    private Integer quantity;
    private Long totalFee;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public OrderDO() {
        //
    }

    public OrderDO(Order order) {
        this.id = order.getId();
        this.itemId = order.getItemId();
        this.buyerId = order.getBuyerId();
        this.sellerId = order.getSellerId();
        this.quantity = order.getQuantity();
        this.totalFee = order.getTotalFee();
        this.gmtCreate = order.getGmtCreate();
        this.gmtModified = order.getGmtModified();
    }
}
