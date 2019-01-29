package io.luan.jerry.address.repository.impl;

import io.luan.jerry.address.data.DeliveryAddressDO;
import io.luan.jerry.address.data.DeliveryAddressMapper;
import io.luan.jerry.address.domain.DeliveryAddress;
import io.luan.jerry.address.factory.DeliveryAddressFactory;
import io.luan.jerry.address.repository.DeliveryAddressRepository;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeliveryAddressRepositoryImpl implements DeliveryAddressRepository {

    private final DeliveryAddressMapper deliveryAddressMapper;

    private final DeliveryAddressFactory deliveryAddressFactory;

    @Autowired
    public DeliveryAddressRepositoryImpl(DeliveryAddressMapper deliveryAddressMapper, DeliveryAddressFactory deliveryAddressFactory) {
        this.deliveryAddressMapper = deliveryAddressMapper;
        this.deliveryAddressFactory = deliveryAddressFactory;
    }

    @Override
    public boolean delete(DeliveryAddress entity) {
        entity.setIsDeleted(true);
        var deliveryAddressDO = new DeliveryAddressDO(entity);
        return this.deliveryAddressMapper.delete(deliveryAddressDO) > 0;
    }

    @Override
    public List<DeliveryAddress> findAll() {
        throw new UnsupportedOperationException("Find all delivery address is not supported.");
    }

    @Override
    public DeliveryAddress findById(@NotNull Long id) {
        var deliveryAddressDO = deliveryAddressMapper.findById(id);
        if (deliveryAddressDO != null) {
            return deliveryAddressFactory.load(deliveryAddressDO);
        }
        return null;
    }

    @Override
    public void save(DeliveryAddress entity) {
        var deliveryAddressDO = new DeliveryAddressDO(entity);
        switch (entity.getState()) {
            case Added:
            case Detached:
                deliveryAddressMapper.insert(deliveryAddressDO);
                entity.setId(deliveryAddressDO.getId());
                break;
            case Modified:
                deliveryAddressMapper.update(deliveryAddressDO);
                break;
        }
        entity.setState(EntityState.Unchanged);
    }

    @Override
    public List<DeliveryAddress> findAllByUserId(Long userId) {
        var doList = deliveryAddressMapper.findAllByUserId(userId);
        return doList.stream().map(deliveryAddressFactory::load).collect(Collectors.toList());
    }

    @Override
    public DeliveryAddress findDefaultByUserId(Long userId) {
        var deliveryAddressDO = deliveryAddressMapper.findDefaultByUserId(userId);
        if (deliveryAddressDO != null) {
            return deliveryAddressFactory.load(deliveryAddressDO);
        }
        return null;
    }

}
