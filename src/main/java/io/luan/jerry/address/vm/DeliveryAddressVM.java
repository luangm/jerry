package io.luan.jerry.address.vm;

import io.luan.jerry.address.domain.DeliveryAddress;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryAddressVM implements Serializable {

    private Long id;
    private Long userId;
    private String receiver;
    private String phone;
    private String address;
    private Boolean isDefault = false;

    public DeliveryAddressVM() {
        // Required by framework
    }

    public DeliveryAddressVM(DeliveryAddress entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.receiver = entity.getReceiver();
        this.phone = entity.getPhone();
        this.address = entity.getAddress().getAddress();
        this.isDefault = entity.getIsDefault();
    }
}
