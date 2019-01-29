package io.luan.jerry.address.factory;

import io.luan.jerry.address.data.DeliveryAddressDO;
import io.luan.jerry.address.domain.Address;
import io.luan.jerry.address.domain.DeliveryAddress;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.stereotype.Component;

@Component
public class DeliveryAddressFactory {

    public DeliveryAddress load(DeliveryAddressDO deliveryAddressDO) {
        var deliveryAddress = new DeliveryAddress();
        deliveryAddress.setId(deliveryAddressDO.getId());
        deliveryAddress.setUserId(deliveryAddressDO.getUserId());
        deliveryAddress.setReceiver(deliveryAddressDO.getReceiver());
        deliveryAddress.setPhone(deliveryAddressDO.getPhone());
        deliveryAddress.setIsDefault(deliveryAddressDO.getIsDefault());
        deliveryAddress.setIsDeleted(deliveryAddressDO.getIsDeleted());
        deliveryAddress.setAddress(new Address(deliveryAddressDO.getAddressAddress()));
        deliveryAddress.setGmtCreate(deliveryAddressDO.getGmtCreate());

        // Note: GmtModified and State should ALWAYS be set last in that order
        deliveryAddress.setGmtModified(deliveryAddressDO.getGmtModified());
        deliveryAddress.setState(EntityState.Unchanged);
        return deliveryAddress;
    }
}
