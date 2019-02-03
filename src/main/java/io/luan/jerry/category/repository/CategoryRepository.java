package io.luan.jerry.category.repository;

import io.luan.jerry.category.domain.Category;
import io.luan.jerry.common.repository.Repository;

public interface CategoryRepository extends Repository<Category, Long> {

    Category findById(Long id, boolean withPV);
}
