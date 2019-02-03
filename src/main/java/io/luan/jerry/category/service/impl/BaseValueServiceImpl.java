package io.luan.jerry.category.service.impl;

import io.luan.jerry.category.domain.BaseValue;
import io.luan.jerry.category.domain.BaseValueState;
import io.luan.jerry.category.repository.BaseValueRepository;
import io.luan.jerry.category.service.BaseValueService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class BaseValueServiceImpl implements BaseValueService {

    private final BaseValueRepository repository;

    @Autowired
    public BaseValueServiceImpl(BaseValueRepository repository) {
        this.repository = repository;
    }

    @Override
    public void disableById(Long id) {
        var baseValue = repository.findById(id);
        baseValue.setStatus(BaseValueState.Disabled);
        repository.save(baseValue);
    }

    @Override
    public void enableById(Long id) {
        var baseValue = repository.findById(id);
        baseValue.setStatus(BaseValueState.Normal);
        repository.save(baseValue);
    }

    @Override
    public List<BaseValue> findAll() {
        return repository.findAll();
    }

    @Override
    public BaseValue findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(BaseValue property) {
        repository.save(property);
    }

}
