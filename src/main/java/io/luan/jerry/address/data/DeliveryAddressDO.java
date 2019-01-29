package io.luan.jerry.address.data;

import io.luan.jerry.address.domain.DeliveryAddress;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class DeliveryAddressDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String receiver;
    private String phone;
    private String addressAddress;
    private Boolean isDefault;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;
    private Boolean isDeleted;

    public DeliveryAddressDO() {
        //
    }

    public DeliveryAddressDO(DeliveryAddress deliveryAddress) {
        this.id = deliveryAddress.getId();
        this.userId = deliveryAddress.getUserId();
        this.receiver = deliveryAddress.getReceiver();
        this.phone = deliveryAddress.getPhone();
        if (deliveryAddress.getAddress() != null) {
            this.addressAddress = deliveryAddress.getAddress().getAddress();
        }
        this.isDefault = deliveryAddress.getIsDefault();
        this.gmtCreate = deliveryAddress.getGmtCreate();
        this.gmtModified = deliveryAddress.getGmtModified();
        this.isDeleted = deliveryAddress.getIsDeleted();
    }
}
