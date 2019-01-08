package io.luan.jerry.buy.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long userId;

    private String source;

    private List<SubOrderDTO> subOrders = new ArrayList<>();

}
