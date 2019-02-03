package io.luan.jerry.category.vm;

import io.luan.jerry.category.domain.BaseValue;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class BaseValueVM implements Serializable {

    private Long id;
    private String value;
    private String status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public BaseValueVM() {
        //
    }

    public BaseValueVM(BaseValue value) {
        this.id = value.getId();
        this.value = value.getValue();
        this.status = value.getStatus().name();
        this.gmtCreate = value.getGmtCreate();
        this.gmtModified = value.getGmtModified();
    }
}
