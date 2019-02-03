package io.luan.jerry.category.service;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.dto.PublishCategoryDTO;

import java.util.List;

public interface CategoryService {

    void disableById(Long id);

    Category edit(PublishCategoryDTO request);

    void enableById(Long id);

    List<Category> findAll();

    Category findById(Long id);

    Category findById(Long id, boolean withPV);

    Category publish(PublishCategoryDTO request);

    void save(Category category);

}
