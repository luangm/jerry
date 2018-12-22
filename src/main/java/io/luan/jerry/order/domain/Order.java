package io.luan.jerry.order.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Long itemId;
    private Long totalFee;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}