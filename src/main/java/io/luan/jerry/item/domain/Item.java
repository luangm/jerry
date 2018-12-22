package io.luan.jerry.item.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Item implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private Long price;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
