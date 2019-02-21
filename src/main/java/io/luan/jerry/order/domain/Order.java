package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.payment.domain.PaymentState;
import io.luan.jerry.shipment.domain.ShipmentState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private OrderState status = OrderState.Created;

    /**
     * Status of payment for this order. Duplicated from Payment
     */
    private PaymentState payStatus = PaymentState.Created;

    /**
     * Status of shipment, Duplicated from Shipment
     */
    private ShipmentState shipStatus = ShipmentState.Created;

    /**
     * Sub Orders
     */
    private List<SubOrder> subOrders = new ArrayList<>();
    /**
     * Attribute Map
     */
    private Map<String, String> attributes = new HashMap<>();

    public void addSubOrder(SubOrder subOrder) {
        this.subOrders.add(subOrder);
        this.calculateTotalFee();
    }

    public void enable() {
        if (this.status == OrderState.Created) {
            firePropertyChange("status", this.status, OrderState.Enabled);
            this.status = OrderState.Enabled;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public long getQuantity() {
        return subOrders.stream().mapToLong(SubOrder::getQuantity).sum();
    }

    /**
     * Set an attribute to the value specified.
     * Set the newValue to null to remove the attribute
     */
    public void setAttribute(String attrName, String newValue) {
        var existing = attributes.get(attrName);

        // Remove existing
        if (newValue == null && existing != null) {
            fireMappedPropertyChange("attributes", attrName, existing, newValue);
            attributes.remove(attrName);
            this.gmtModified = OffsetDateTime.now().withNano(0);
            return;
        }

        // Set new value
        if (newValue != null && !newValue.equals(existing)) {
            fireMappedPropertyChange("attributes", attrName, existing, newValue);
            attributes.put(attrName, newValue);
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setPayStatus(PaymentState newValue) {
        if (!newValue.equals(this.payStatus)) {
            firePropertyChange("payStatus", this.payStatus, newValue);
            this.payStatus = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setShipStatus(ShipmentState newValue) {
        if (!newValue.equals(this.shipStatus)) {
            firePropertyChange("shipStatus", this.shipStatus, newValue);
            this.shipStatus = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    private void calculateTotalFee() {
        this.totalFee = subOrders.stream().mapToLong(SubOrder::getTotalFee).sum();
    }
}