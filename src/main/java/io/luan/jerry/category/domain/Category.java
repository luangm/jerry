package io.luan.jerry.category.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Category is aggregate root for CategoryProperty,
 * since no CategoryProperty can be read without reading in Category first.
 */
@Data
public class Category implements Serializable {

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
     * Time when the category is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the category is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();

}
