package io.luan.jerry.user.data;

import io.luan.jerry.user.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public UserDO() {
        //
    }

    public UserDO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.gmtCreate = user.getGmtCreate();
        this.gmtModified = user.getGmtModified();
    }

}
