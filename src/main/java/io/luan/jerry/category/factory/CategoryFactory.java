package io.luan.jerry.category.factory;

import io.luan.jerry.category.data.CategoryDO;
import io.luan.jerry.category.data.CategoryPropertyDO;
import io.luan.jerry.category.data.CategoryPropertyValueDO;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.domain.CategoryProperty;
import io.luan.jerry.category.domain.CategoryPropertyValue;
import io.luan.jerry.category.domain.CategoryState;
import io.luan.jerry.common.domain.EntityState;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryFactory {

    public Category load(CategoryDO categoryDO) {
        var category = new Category();
        category.setId(categoryDO.getId());
        category.setName(categoryDO.getName());
        category.setIsLeaf(categoryDO.getIsLeaf());
        category.setParentId(categoryDO.getParentId());
        category.setGmtCreate(categoryDO.getGmtCreate());
        category.setStatus(CategoryState.fromValue(categoryDO.getStatus()));

        // Note: GmtModified and State should ALWAYS be set last in that order
        category.setGmtModified(categoryDO.getGmtModified());
        category.setState(EntityState.Unchanged);
        return category;
    }

    public Category load(CategoryDO categoryDO, List<CategoryPropertyDO> cpDoList, List<CategoryPropertyValueDO> cpvDoList) {

        var category = load(categoryDO);

        Map<CPKey, CategoryPropertyDO> cpMap = new HashMap<>();
        Map<CPKey, List<CategoryPropertyValueDO>> cpvMap = new HashMap<>();

        for (var cpDO : cpDoList) {
            var key = new CPKey(cpDO.getCategoryId(), cpDO.getPropertyId());
            cpMap.put(key, cpDO);
            cpvMap.put(key, new ArrayList<>());
        }

        for (var cpvDO : cpvDoList) {
            var key = new CPKey(cpvDO.getCategoryId(), cpvDO.getPropertyId());
            var list = cpvMap.get(key);
            if (list != null) {
                list.add(cpvDO);
            }
        }

        List<CategoryProperty> cpList = new ArrayList<>();
        for (Map.Entry<CPKey, CategoryPropertyDO> entry : cpMap.entrySet()) {
            CategoryProperty cp = load(category, entry.getValue(), cpvMap.get(entry.getKey()));
            cpList.add(cp);
        }
        cpList.sort(Comparator.comparingLong(CategoryProperty::getSortOrder));
        category.setProperties(cpList);

        // Note: GmtModified and State should ALWAYS be set last in that order
        category.setGmtModified(categoryDO.getGmtModified());
        category.setState(EntityState.Unchanged);

        return category;
    }

    private CategoryProperty load(Category category, CategoryPropertyDO cpDO, List<CategoryPropertyValueDO> cpvDoList) {
        var cp = new CategoryProperty();
        cp.setCategory(category);
        cp.setPropertyId(cpDO.getPropertyId());
        cp.setAlias(cpDO.getAlias());
        cp.setSortOrder(cpDO.getSortOrder());
        cp.setGmtCreate(cpDO.getGmtCreate());


        List<CategoryPropertyValue> cpvList = new ArrayList<>();
        for (CategoryPropertyValueDO categoryPropertyValueDO : cpvDoList) {
            CategoryPropertyValue cpv = load(cp, categoryPropertyValueDO);
            cpvList.add(cpv);
        }
        cpvList.sort(Comparator.comparingLong(CategoryPropertyValue::getSortOrder));
        cp.setValues(cpvList);

        // Note: GmtModified and State should ALWAYS be set last in that order
        cp.setGmtModified(cpDO.getGmtModified());
        cp.setState(EntityState.Unchanged);
        return cp;
    }

    private CategoryPropertyValue load(CategoryProperty cp, CategoryPropertyValueDO cpvDO) {
        var cpv = new CategoryPropertyValue();
        cpv.setCategoryProperty(cp);
        cpv.setValueId(cpvDO.getValueId());
        cpv.setAlias(cpvDO.getAlias());
        cpv.setSortOrder(cpvDO.getSortOrder());
        cpv.setGmtCreate(cpvDO.getGmtCreate());

        // Note: GmtModified and State should ALWAYS be set last in that order
        cpv.setGmtModified(cpvDO.getGmtModified());
        cpv.setState(EntityState.Unchanged);
        return cpv;
    }

    @Value
    private static class CPKey {
        private Long categoryId;
        private Long propertyId;
    }
}
