package io.luan.jerry.category.vm;

import io.luan.jerry.category.domain.Category;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class CategoryVM implements Serializable {

    private Long id;
    private Long parentId = 0L;
    private String name;
    private Boolean isLeaf = false;
    private String status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public CategoryVM() {
        //
    }

    public CategoryVM(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.name = category.getName();
        this.isLeaf = category.getIsLeaf();
        this.status = category.getStatus().name();
        this.gmtCreate = category.getGmtCreate();
        this.gmtModified = category.getGmtModified();
    }
}
