package io.luan.jerry.buy.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ConfirmOrderRequest implements Serializable {

    private Long userId;

    private String source;

    private String address;

    private List<OrderLineDTO> orderLines = new ArrayList<>();

    public void addOrderLine(Long itemId, Integer quantity) {
        var orderLine = new OrderLineDTO(itemId, quantity);
        this.orderLines.add(orderLine);
    }

    public List<Long> getItemIds() {
        return orderLines.stream().map(OrderLineDTO::getItemId).distinct().collect(Collectors.toList());
    }
}
