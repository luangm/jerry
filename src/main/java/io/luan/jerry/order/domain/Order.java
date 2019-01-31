package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.payment.domain.PaymentStatus;
import io.luan.jerry.shipment.domain.ShipmentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Order extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Order ID
     */
    private Long id;

    /**
     * Buyer ID
     */
    private Long buyerId;

    /**
     * Seller ID
     */
    private Long sellerId;

    /**
     * Total Fee
     */
    private Long totalFee;
    
    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    /**
     * Status of the entire order
     */
    private OrderStatus status = OrderStatus.Created;

    /**
     * Status of payment for this order. Duplicated from Payment
     */
    private PaymentStatus payStatus = PaymentStatus.Created;

    /**
     * Status of shipment, Duplicated from Shipment
     */
    private ShipmentStatus shipStatus = ShipmentStatus.Created;

    /**
     * Sub Orders
     */
    private List<SubOrder> subOrders = new ArrayList<>();

    public void addSubOrder(SubOrder subOrder) {
        this.subOrders.add(subOrder);
        this.calculateTotalFee();
    }

    public int getQuantity() {
        return subOrders.stream().mapToInt(SubOrder::getQuantity).sum();
    }

    private void calculateTotalFee() {
        this.totalFee = subOrders.stream().mapToLong(SubOrder::getTotalFee).sum();
    }

    public void setPayStatus(PaymentStatus newValue) {
        if (!newValue.equals(this.payStatus)) {
            firePropertyChange("payStatus", this.payStatus, newValue);
            this.payStatus = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setShipStatus(ShipmentStatus newValue) {
        if (!newValue.equals(this.shipStatus)) {
            firePropertyChange("shipStatus", this.shipStatus, newValue);
            this.shipStatus = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void enable() {
        if (this.status == OrderStatus.Created) {
            firePropertyChange("status", this.status, OrderStatus.Enabled);
            this.status = OrderStatus.Enabled;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }
}