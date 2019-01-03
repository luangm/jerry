package io.luan.jerry.category.service.impl;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.dto.PublishCategoryDTO;
import io.luan.jerry.category.repository.CategoryRepository;
import io.luan.jerry.category.service.CategoryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category edit(PublishCategoryDTO request) {
        var category = categoryRepository.findById(request.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("category not found");
        }

        category.setName(request.getName());
        category.setIsLeaf(request.getIsLeaf());
        category.setGmtModified(LocalDateTime.now());

        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category publish(PublishCategoryDTO request) {
        var category = new Category();
        category.setName(request.getName());
        category.setParentId(request.getParentId());
        category.setIsLeaf(request.getIsLeaf());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

}
