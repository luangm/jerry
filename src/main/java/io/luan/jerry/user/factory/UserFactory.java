package io.luan.jerry.user.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.user.data.UserDO;
import io.luan.jerry.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public User load(UserDO userDO) {
        var user = new User();
        user.setId(userDO.getId());
        user.setUsername(userDO.getUsername());
        user.setPassword(userDO.getPassword());
        user.setGmtCreate(userDO.getGmtCreate());

        // Note: GmtModified and State should ALWAYS be set last in that order
        user.setGmtModified(userDO.getGmtModified());
        user.setState(EntityState.Unchanged);
        return user;
    }
}
