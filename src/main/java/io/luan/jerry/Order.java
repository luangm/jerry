package io.luan.jerry;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    private long id;
    private long userId;
    private long itemId;
    private long totalFee;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}