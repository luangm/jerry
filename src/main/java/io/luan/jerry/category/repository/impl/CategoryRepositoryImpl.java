package io.luan.jerry.category.repository.impl;

import io.luan.jerry.category.data.*;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.domain.CategoryProperty;
import io.luan.jerry.category.domain.CategoryPropertyValue;
import io.luan.jerry.category.factory.CategoryFactory;
import io.luan.jerry.category.repository.CategoryRepository;
import io.luan.jerry.common.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    private final CategoryFactory categoryFactory;

    private final CategoryPropertyMapper cpMapper;

    private final CategoryPropertyValueMapper cpvMapper;

    @Autowired
    public CategoryRepositoryImpl(CategoryMapper categoryMapper, CategoryFactory categoryFactory, CategoryPropertyMapper cpMapper, CategoryPropertyValueMapper cpvMapper) {
        this.categoryMapper = categoryMapper;
        this.categoryFactory = categoryFactory;
        this.cpMapper = cpMapper;
        this.cpvMapper = cpvMapper;
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
    @Transactional
    public void save(Category category) {
        RepositoryHelper.save(category, this::insert, this::update);
        category.getProperties().forEach(this::save);
    }

    @Override
    public Category findById(Long id, boolean withPV) {
        if (!withPV) {
            return findById(id);
        }

        var categoryDO = categoryMapper.findById(id);
        if (categoryDO != null) {
            var cpDoList = cpMapper.findAllByCategoryId(id);
            var cpvDoList = cpvMapper.findAllByCategoryId(id);
            return categoryFactory.load(categoryDO, cpDoList, cpvDoList);
        }
        return null;
    }

    private void insert(Category category) {
        var categoryDO = new CategoryDO(category);
        categoryMapper.insert(categoryDO);
        var catId = categoryDO.getId();
        category.setId(catId);
    }

    private void insert(CategoryProperty cp) {
        var cpDO = new CategoryPropertyDO(cp);
        cpMapper.insert(cpDO);
    }

    private void insert(CategoryPropertyValue cpv) {
        var cpvDO = new CategoryPropertyValueDO(cpv);
        cpvMapper.insert(cpvDO);
    }

    private void save(CategoryProperty cp) {
        RepositoryHelper.save(cp, this::insert, this::update);
        cp.getValues().forEach(this::save);
    }

    private void save(CategoryPropertyValue cpv) {
        RepositoryHelper.save(cpv, this::insert, this::update);
    }

    private void update(CategoryProperty cp) {
        var cpDO = new CategoryPropertyDO(cp);
        cpMapper.update(cpDO);
    }

    private void update(CategoryPropertyValue cpv) {
        var cpvDO = new CategoryPropertyValueDO(cpv);
        cpvMapper.update(cpvDO);
    }

    private void update(Category category) {
        var categoryDO = new CategoryDO(category);
        categoryMapper.update(categoryDO);
    }

}
