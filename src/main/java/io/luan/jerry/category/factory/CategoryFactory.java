package io.luan.jerry.category.factory;

import io.luan.jerry.category.data.CategoryDO;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

    public Category loadFromDataObject(CategoryDO categoryDO) {
        var category = new Category();
        category.setId(categoryDO.getId());
        category.setName(categoryDO.getName());
        category.setIsLeaf(categoryDO.getIsLeaf());
        category.setParentId(categoryDO.getParentId());
        category.setGmtCreate(categoryDO.getGmtCreate());
        category.setGmtModified(categoryDO.getGmtModified());
        category.setState(EntityState.Unchanged);
        return category;
    }

}
