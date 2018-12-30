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
    private Integer amount;
    private Long totalFee;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public static OrderDO fromEntity(Order order) {
        var orderDO = new OrderDO();
        orderDO.setId(order.getId());
        orderDO.setItemId(order.getItemId());
        orderDO.setBuyerId(order.getBuyerId());
        orderDO.setSellerId(order.getSellerId());
        orderDO.setAmount(order.getAmount());
        orderDO.setTotalFee(order.getTotalFee());
        orderDO.setGmtCreate(order.getGmtCreate());
        orderDO.setGmtModified(order.getGmtModified());
        return orderDO;
    }

    public Order toEntity() {
        var order = new Order();
        order.setId(this.getId());
        order.setBuyerId(this.getBuyerId());
        order.setSellerId(this.getSellerId());
        order.setItemId(this.getItemId());
        order.setAmount(this.getAmount());
        order.setTotalFee(this.getTotalFee());
        order.setGmtCreate(this.getGmtCreate());
        order.setGmtModified(this.getGmtModified());
        return order;
    }
}
