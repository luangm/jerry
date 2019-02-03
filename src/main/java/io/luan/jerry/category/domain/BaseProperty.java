package io.luan.jerry.category.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseProperty extends Entity {

    static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    /**
     * When a Property is disabled, it can no longer be selected
     */
    private BasePropertyState status = BasePropertyState.Normal;

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public void setName(String newValue) {
        if (!newValue.equals(this.name)) {
            firePropertyChange("name", name, newValue);
            this.name = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setStatus(BasePropertyState newValue) {
        if (!newValue.equals(this.status)) {
            firePropertyChange("status", status, newValue);
            this.status = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }
}
