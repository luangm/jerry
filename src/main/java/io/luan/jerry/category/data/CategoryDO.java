package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.Category;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String name;
    private Boolean isLeaf;
    private Integer status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public CategoryDO() {
        //
    }

    public CategoryDO(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.name = category.getName();
        this.isLeaf = category.getIsLeaf() != null ? category.getIsLeaf() : false;
        this.status = category.getStatus().getValue();
        this.gmtCreate = category.getGmtCreate();
        this.gmtModified = category.getGmtModified();
    }

}
