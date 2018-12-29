package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.Category;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String name;
    private Boolean isLeaf;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public static CategoryDO fromEntity(Category category) {
        var categoryDO = new CategoryDO();
        categoryDO.setId(category.getId());
        categoryDO.setParentId(category.getParentId());
        categoryDO.setName(category.getName());
        categoryDO.setIsLeaf(category.getIsLeaf() != null ? category.getIsLeaf() : false);
        categoryDO.setGmtCreate(category.getGmtCreate());
        categoryDO.setGmtModified(category.getGmtModified());
        return categoryDO;
    }

    public Category toEntity() {
        var category = new Category();
        category.setId(this.getId());
        category.setIsLeaf(this.getIsLeaf());
        category.setParentId(this.getParentId());
        category.setName(this.getName());
        category.setGmtCreate(this.getGmtCreate());
        category.setGmtModified(this.getGmtModified());
        return category;
    }
}
