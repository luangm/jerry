package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.payment.domain.PaymentState;
import io.luan.jerry.shipment.domain.ShipmentState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderLine extends Entity {

    /**
     * OrderLine ID
     */
    private Long id;

    /**
     * Parent ID
     */
    private Long parentId;

    /**
     * Buyer ID
     */
    private Long buyerId;

    /**
     * Seller ID
     */
    private Long sellerId;

    /**
     * Item ID
     */
    private Long itemId;

    /**
     * Original Price for Item, redundant field
     */
    private Long itemPrice;

    /**
     * Item's title, redundant field
     */
    private String itemTitle;

    /**
     * Item's image, redundant field
     */
    private String itemImgUrl;

    /**
     * Quantity of Item
     */
    private Long quantity;

    /**
     * Total Discounts (calculated by buy)
     */
    private Long discountFee = 0L;

    /**
     * Status of the sub order
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
     * Attribute Map
     */
    private Map<String, String> attributes = new HashMap<>();

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public String getAttribute(String attrName) {
        return attributes.get(attrName);
    }

    public Long getSkuId() {
        var attr = attributes.get(OrderAttributes.SKU_ID);
        if (attr != null) {
            return Long.parseLong(attr);
        }
        return null;
    }

    public void setSkuId(Long skuId) {
        if (skuId != null) {
            attributes.put(OrderAttributes.SKU_ID, skuId.toString());
        } else {
            attributes.remove(OrderAttributes.SKU_ID);
        }
    }

    public Long getTotalFee() {
        return this.getItemPrice() * this.getQuantity() - this.getDiscountFee();
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
}
