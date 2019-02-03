package io.luan.jerry.category.service;

import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.dto.PublishCategoryDTO;

import java.util.List;

public interface BasePropertyService {

    void disableById(Long id);

    void enableById(Long id);

    List<BaseProperty> findAll();

    BaseProperty findById(Long id);

    void save(BaseProperty property);

}
