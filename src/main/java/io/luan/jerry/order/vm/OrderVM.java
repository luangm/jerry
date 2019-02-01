package io.luan.jerry.order.vm;

import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.OrderStatus;
import io.luan.jerry.payment.domain.PaymentStatus;
import io.luan.jerry.shipment.domain.ShipmentStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderVM implements Serializable {

    private Long id;

    private Long buyerId;

    private Long sellerId;

    private Long totalFee;

    private OffsetDateTime gmtCreate;

    private OffsetDateTime gmtModified;

    private String status;

    private String payStatus;

    private String shipStatus;

    private List<SubOrderVM> subOrders = new ArrayList<>();

    private Long quantity;

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
        this.status = order.getStatus().name();
        this.payStatus = order.getPayStatus().name();
        this.shipStatus = order.getShipStatus().name();
        this.quantity = order.getQuantity();
        this.subOrders = order.getSubOrders().stream().map(SubOrderVM::new).collect(Collectors.toList());
    }
}
