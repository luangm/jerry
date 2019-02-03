package io.luan.jerry.category.factory;

import io.luan.jerry.category.data.BasePropertyDO;
import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.domain.BasePropertyState;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.stereotype.Component;

@Component
public class BasePropertyFactory {

    public BaseProperty load(BasePropertyDO propertyDO) {
        var property = new BaseProperty();
        property.setId(propertyDO.getId());
        property.setName(propertyDO.getName());
        property.setStatus(BasePropertyState.fromValue(propertyDO.getStatus()));
        property.setGmtCreate(propertyDO.getGmtCreate());

        // Note: GmtModified and State should ALWAYS be set last in that order
        property.setGmtModified(propertyDO.getGmtModified());
        property.setState(EntityState.Unchanged);
        return property;
    }

}
