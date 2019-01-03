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

    public CategoryDO() {
        //
    }

    public CategoryDO(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.name = category.getName();
        this.isLeaf = category.getIsLeaf() != null ? category.getIsLeaf() : false;
        this.gmtCreate = category.getGmtCreate();
        this.gmtModified = category.getGmtModified();
    }

}
