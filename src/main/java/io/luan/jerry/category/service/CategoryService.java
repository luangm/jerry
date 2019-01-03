package io.luan.jerry.category.service;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.dto.PublishCategoryDTO;

import java.util.List;

public interface CategoryService {

    Category edit(PublishCategoryDTO request);

    List<Category> findAll();

    Category findById(Long id);

    Category publish(PublishCategoryDTO request);

    void save(Category category);

}
