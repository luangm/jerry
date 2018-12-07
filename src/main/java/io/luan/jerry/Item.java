package io.luan.jerry;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Item {

    private long id;
    private String title;
    private long price;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
