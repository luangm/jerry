package io.luan.jerry.address.service.impl;

import io.luan.jerry.address.domain.DeliveryAddress;
import io.luan.jerry.address.repository.DeliveryAddressRepository;
import io.luan.jerry.address.service.DeliveryAddressService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository repository;

    @Autowired
    public DeliveryAddressServiceImpl(DeliveryAddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean delete(DeliveryAddress deliveryAddress) {
        return repository.delete(deliveryAddress);
    }

    @Override
    public List<DeliveryAddress> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public DeliveryAddress findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public DeliveryAddress findDefaultByUserId(Long userId) {
        return repository.findDefaultByUserId(userId);
    }

    @Override
    public DeliveryAddress save(DeliveryAddress entity) {
        repository.save(entity);
        return entity;
    }

}
