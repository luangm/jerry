package io.luan.jerry.category.service;

import io.luan.jerry.category.domain.BaseValue;

import java.util.List;

public interface BaseValueService {

    void disableById(Long id);

    void enableById(Long id);

    List<BaseValue> findAll();

    BaseValue findById(Long id);

    void save(BaseValue value);

}
