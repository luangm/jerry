package io.luan.jerry.shipment.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.common.domain.EntityState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Shipment extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Shipment ID
     */
    private Long id;

    /**
     * Order ID
     */
    private Long orderId;

    /**
     * Buyer ID
     */
    private Long buyerId;

    /**
     * Seller ID
     */
    private Long sellerId;

    /**
     * Shipment Method
     */
    private ShipmentMethod method;

    /**
     * Address
     */
    private String address;

    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

    /**
     * Shipment Status
     */
    private ShipmentStatus status = ShipmentStatus.Created;

    public Shipment() {
        this.setState(EntityState.Added);
    }

    public void receive() {
        this.setStatus(ShipmentStatus.Received);
    }

    public void setStatus(ShipmentStatus newValue) {
        if (!newValue.equals(this.status)) {
            firePropertyChange("status", this.status, newValue);
            this.status = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }

    public void ship() {
        this.setStatus(ShipmentStatus.Shipped);
    }
}