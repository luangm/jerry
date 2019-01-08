package io.luan.jerry.buy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SubOrderDTO implements Serializable {

    static final long serialVersionUID = 1L;

    @NotNull
    private Long itemId;

    @NotNull
    private Integer quantity;

    public SubOrderDTO(Long itemId, Integer quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
