package io.luan.jerry.category.service.impl;

import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.domain.BasePropertyState;
import io.luan.jerry.category.domain.BaseValueState;
import io.luan.jerry.category.repository.BasePropertyRepository;
import io.luan.jerry.category.service.BasePropertyService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class BasePropertyServiceImpl implements BasePropertyService {

    private final BasePropertyRepository repository;

    @Autowired
    public BasePropertyServiceImpl(BasePropertyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void disableById(Long id) {
        var prop = repository.findById(id);
        prop.setStatus(BasePropertyState.Disabled);
        repository.save(prop);
    }

    @Override
    public void enableById(Long id) {
        var prop = repository.findById(id);
        prop.setStatus(BasePropertyState.Normal);
        repository.save(prop);
    }

    @Override
    public List<BaseProperty> findAll() {
        return repository.findAll();
    }

    @Override
    public BaseProperty findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(BaseProperty property) {
        repository.save(property);
    }

}
