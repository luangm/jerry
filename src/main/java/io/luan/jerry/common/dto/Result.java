package io.luan.jerry.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    static final long serialVersionUID = 1L;

    T data;

    boolean success;

    String message;

}
