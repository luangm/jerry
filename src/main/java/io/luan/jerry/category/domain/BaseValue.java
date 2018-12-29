package io.luan.jerry.category.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseValue implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;

    private String data;

    /**
     * Time when the value is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the value is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();
}
