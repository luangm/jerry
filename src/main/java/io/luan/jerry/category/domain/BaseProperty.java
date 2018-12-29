package io.luan.jerry.category.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseProperty implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    /**
     * Time when the property is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the property is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();
}
