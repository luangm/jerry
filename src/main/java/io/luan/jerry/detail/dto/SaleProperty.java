package io.luan.jerry.detail.dto;

import io.luan.jerry.category.domain.CategoryProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SaleProperty implements Serializable {

    private Long propertyId;

    private String alias;

    private List<PropertyValue> values;

    public SaleProperty(CategoryProperty cp) {
        this.propertyId = cp.getPropertyId();
        this.alias = cp.getAlias();
        this.values = cp.getValues().stream().map(PropertyValue::new).collect(Collectors.toList());
    }
}
