package io.luan.jerry.buy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderLineDTO implements Serializable {

    static final long serialVersionUID = 1L;

    @NotNull
    private Long itemId;

    @NotNull
    private Long quantity;

    public OrderLineDTO(Long itemId, Long quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
