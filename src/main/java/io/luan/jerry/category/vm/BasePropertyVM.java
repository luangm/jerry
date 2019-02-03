package io.luan.jerry.category.vm;

import io.luan.jerry.category.domain.BaseProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class BasePropertyVM implements Serializable {

    private Long id;
    private String name;
    private String status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public BasePropertyVM() {
        //
    }

    public BasePropertyVM(BaseProperty property) {
        this.id = property.getId();
        this.name = property.getName();
        this.status = property.getStatus().name();
        this.gmtCreate = property.getGmtCreate();
        this.gmtModified = property.getGmtModified();
    }
}
