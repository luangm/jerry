package io.luan.jerry.category.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishCategoryDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long categoryId;
    private Long parentId;
    private String name;
    private Boolean isLeaf;

}
