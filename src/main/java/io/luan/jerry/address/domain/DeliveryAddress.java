package io.luan.jerry.address.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryAddress extends Entity {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String receiver;

    private String phone;

    private Address address;

    private Boolean isDefault = false;

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    /**
     * Deleted
     */
    private Boolean isDeleted = false;

    public DeliveryAddress() {
        //
    }

    public void setReceiver(String newValue) {
        if (!newValue.equals(this.receiver)) {
            firePropertyChange("receiver", receiver, newValue);
            this.receiver = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setPhone(String newValue) {
        if (!newValue.equals(this.phone)) {
            firePropertyChange("phone", phone, newValue);
            this.phone = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setIsDeleted(Boolean newValue) {
        if (!newValue.equals(this.isDeleted)) {
            firePropertyChange("isDeleted", isDeleted, newValue);
            this.isDeleted = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setAddress(Address newValue) {
        if (!newValue.equals(this.address)) {
            firePropertyChange("address", address, newValue);
            this.address = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setIsDefault(Boolean newValue) {
        if (!newValue.equals(this.isDefault)) {
            firePropertyChange("isDefault", isDefault, newValue);
            this.isDefault = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }
}
