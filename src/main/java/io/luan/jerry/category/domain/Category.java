package io.luan.jerry.category.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
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
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

}
