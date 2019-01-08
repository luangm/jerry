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

    private List<SubOrderDTO> subOrders = new ArrayList<>();

    public List<Long> getItemIds() {
        return subOrders.stream().map(SubOrderDTO::getItemId).distinct().collect(Collectors.toList());
    }
}
