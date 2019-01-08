package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class SubOrder extends Entity {

    /**
     * SubOrder ID
     */
    private Long id;

    /**
     * Parent ID
     */
    private Long parentId;

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
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

}