package io.luan.jerry.category.repository.impl;

import io.luan.jerry.category.data.CategoryDO;
import io.luan.jerry.category.data.CategoryMapper;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryRepositoryImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public boolean delete(Category entity) {
        var categoryDO = CategoryDO.fromEntity(entity);
        if (categoryDO.getId() != null) {
            int count = categoryMapper.delete(categoryDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        for (CategoryDO categoryDO : categoryMapper.findAll()) {
            var category = categoryDO.toEntity();
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        var categoryDO = categoryMapper.findById(id);
        if (categoryDO != null) {
            return categoryDO.toEntity();
        }
        return null;
    }

    @Override
    public Category save(Category entity) {
        var categoryDO = CategoryDO.fromEntity(entity);
        if (categoryDO.getId() == null) {
            categoryMapper.insert(categoryDO);
            return findById(categoryDO.getId());
        } else {
            categoryMapper.update(categoryDO);
            return entity;
        }
    }

}
