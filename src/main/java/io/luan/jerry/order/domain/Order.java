package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
     * Seller ID
     */
    private Long sellerId;

    /**
     * Item ID
     */
    private Long itemId;

    /**
     * Quantity of Item
     */
    private Integer quantity;

    /**
     * Total cost
     */
    private Long totalFee;

    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now();

}