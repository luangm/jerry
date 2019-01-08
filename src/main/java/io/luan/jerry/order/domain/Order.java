package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Order extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Order ID
     */
    private Long id;

    /**
     * Buyer ID
     */
    private Long buyerId;

    /**
     * Total Fee
     */
    private Long totalFee;
    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

    /**
     * Sub Orders
     */
    private List<SubOrder> subOrders = new ArrayList<>();

    public void addSubOrder(SubOrder subOrder) {
        this.subOrders.add(subOrder);
        this.calculateTotalFee();
    }

    public int getQuantity() {
        return subOrders.stream().mapToInt(SubOrder::getQuantity).sum();
    }

    private void calculateTotalFee() {
        this.totalFee = subOrders.stream().mapToLong(SubOrder::getTotalFee).sum();
    }
}