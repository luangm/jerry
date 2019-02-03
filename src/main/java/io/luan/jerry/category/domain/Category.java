package io.luan.jerry.category.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Category is aggregate root for CategoryProperty,
 * since no CategoryProperty can be read without reading in Category first.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Category ID
     */
    private Long id;

    /**
     * Parent category's ID
     */
    private Long parentId = 0L;

    /**
     * Name of the category
     */
    private String name;

    /**
     * Whether this category is a leaf node
     */
    private Boolean isLeaf;

    /**
     * The list of properties that this category can have
     */
    private List<CategoryProperty> properties = new ArrayList<>();

    /**
     * When a category is disabled, it cannot be selected
     */
    private CategoryState status = CategoryState.Normal;

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public CategoryProperty addProperty(Long propId, String alias) {
        var cp = new CategoryProperty();
        cp.setCategory(this);
        cp.setPropertyId(propId);
        cp.setAlias(alias);
        cp.setSortOrder(1L);
        this.properties.add(cp);
        return cp;
    }

    public void setStatus(CategoryState newValue) {
        if (!newValue.equals(this.status)) {
            firePropertyChange("status", status, newValue);
            this.status = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setName(String newValue) {
        if (!newValue.equals(this.name)) {
            firePropertyChange("name", name, newValue);
            this.name = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setParentId(Long newValue) {
        if (!newValue.equals(this.parentId)) {
            firePropertyChange("parentId", parentId, newValue);
            this.parentId = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setIsLeaf(Boolean newValue) {
        if (!newValue.equals(this.isLeaf)) {
            firePropertyChange("isLeaf", isLeaf, newValue);
            this.isLeaf = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

}
