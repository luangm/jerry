package io.luan.jerry.category.vm;

import io.luan.jerry.category.domain.CategoryProperty;
import io.luan.jerry.category.domain.PropertyOption;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryPropertyVM implements Serializable {

    private Long categoryId;
    private Long propertyId;
    private String alias;
    private Long sortOrder;
    private Integer type = 0;
    private List<Integer> options;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public CategoryPropertyVM() {
        //
    }

    public CategoryPropertyVM(CategoryProperty cp) {
        this.categoryId = cp.getCategory().getId();
        this.propertyId = cp.getPropertyId();
        this.alias = cp.getAlias();
        this.sortOrder = cp.getSortOrder();
        this.type = cp.getPropertyType().getValue();
        this.options = cp.getOptions().stream().map(PropertyOption::getBit).collect(Collectors.toList());
        this.gmtCreate = cp.getGmtCreate();
        this.gmtModified = cp.getGmtModified();
    }
}
