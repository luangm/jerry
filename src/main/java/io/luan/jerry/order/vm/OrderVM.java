package io.luan.jerry.order.vm;

import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderStatus;
import io.luan.jerry.payment.domain.PaymentStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderVM implements Serializable {

    private Long id;

    private Long buyerId;

    private Long sellerId;

    private Long totalFee;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private OrderStatus status;

    private PaymentStatus payStatus;

    private List<SubOrderVM> subOrders = new ArrayList<>();

    private Integer quantity;

    public OrderVM() {
        //
    }

    public OrderVM(Order order) {
        this.id = order.getId();
        this.buyerId = order.getBuyerId();
        this.sellerId = order.getSellerId();
        this.totalFee = order.getTotalFee();
        this.gmtCreate = order.getGmtCreate();
        this.gmtModified = order.getGmtModified();
        this.status = order.getStatus();
        this.payStatus = order.getPayStatus();
        this.quantity = order.getQuantity();
        this.subOrders = order.getSubOrders().stream().map(SubOrderVM::new).collect(Collectors.toList());
    }
}
