package io.luan.jerry.category.repository.impl;

import io.luan.jerry.category.data.CategoryDO;
import io.luan.jerry.category.data.CategoryMapper;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.factory.CategoryFactory;
import io.luan.jerry.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    private final CategoryFactory categoryFactory;

    @Autowired
    public CategoryRepositoryImpl(CategoryMapper categoryMapper, CategoryFactory categoryFactory) {
        this.categoryMapper = categoryMapper;
        this.categoryFactory = categoryFactory;
    }

    @Override
    public boolean delete(Category entity) {
        var categoryDO = new CategoryDO(entity);
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
            var category = categoryFactory.load(categoryDO);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        var categoryDO = categoryMapper.findById(id);
        if (categoryDO != null) {
            return categoryFactory.load(categoryDO);
        }
        return null;
    }

    @Override
    public void save(Category category) {
        var categoryDO = new CategoryDO(category);
        if (categoryDO.getId() == null) {
            categoryMapper.insert(categoryDO);
            category.setId(categoryDO.getId());
        } else {
            categoryMapper.update(categoryDO);
        }
    }

}
