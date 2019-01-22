package io.luan.jerry.buy.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long userId;

    private String source;

    private String address;

    private List<OrderLineDTO> orderLines = new ArrayList<>();

    public List<Long> getItemIds() {
        return orderLines.stream().map(OrderLineDTO::getItemId).distinct().collect(Collectors.toList());
    }
}
