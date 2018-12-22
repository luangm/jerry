package io.luan.jerry.user.data;

import io.luan.jerry.user.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private String nick;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public static UserDO fromEntity(User user) {
        var userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setNick(user.getNick());
        userDO.setGmtCreate(user.getGmtCreate());
        userDO.setGmtModified(user.getGmtModified());
        return userDO;
    }

    public User toEntity() {
        var user = new User();
        user.setId(this.getId());
        user.setNick(this.getNick());
        user.setGmtCreate(this.getGmtCreate());
        user.setGmtModified(this.getGmtModified());
        return user;
    }
}
