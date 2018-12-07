package io.luan.jerry;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private long id;
    private String nick;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
