package io.luan.jerry.category.service.impl;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.domain.CategoryState;
import io.luan.jerry.category.dto.PublishCategoryDTO;
import io.luan.jerry.category.repository.CategoryRepository;
import io.luan.jerry.category.service.CategoryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Log
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    @Override
    public void disableById(Long id) {
        var baseValue = repository.findById(id);
        baseValue.setStatus(CategoryState.Disabled);
        repository.save(baseValue);
    }

    @Override
    public Category edit(PublishCategoryDTO request) {
        var category = repository.findById(request.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("category not found");
        }

        category.setName(request.getName());
        category.setIsLeaf(request.getIsLeaf());
        category.setGmtModified(OffsetDateTime.now().withNano(0));

        repository.save(category);
        return category;
    }

    @Override
    public void enableById(Long id) {
        var baseValue = repository.findById(id);
        baseValue.setStatus(CategoryState.Normal);
        repository.save(baseValue);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Category findById(Long id, boolean withPV) {
        return repository.findById(id, withPV);
    }

    @Override
    @Transactional
    public Category publish(PublishCategoryDTO request) {
        if (request.getParentId() != null && request.getParentId() != 0) {
            var parent = repository.findById(request.getParentId());
            if (parent.getIsLeaf()) {
                parent.setIsLeaf(false);
                repository.save(parent);
            }
        }

        var category = new Category();
        category.setName(request.getName());
        category.setParentId(request.getParentId());
        category.setIsLeaf(true);
        repository.save(category);

        return category;
    }

    @Override
    public void save(Category category) {
        repository.save(category);
    }

}
