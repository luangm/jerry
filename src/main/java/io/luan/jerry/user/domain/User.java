package io.luan.jerry.user.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private String nick;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
