package io.luan.jerry.buy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateOrderDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long userId;

    @NotNull
    private Long itemId;

    @NotNull
    private Integer amount;

}
