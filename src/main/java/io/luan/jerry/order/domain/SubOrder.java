package io.luan.jerry.order.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.payment.domain.PaymentStatus;
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
     * Original Price for Item, redundant field
     */
    private Long itemPrice;

    /**
     * Item's title, redundant field
     */
    private String itemTitle;

    /**
     * Item's image, redundant field
     */
    private String itemImgUrl;

    /**
     * Quantity of Item
     */
    private Integer quantity;

    /**
     * Total Discounts (calculated by buy)
     */
    private Long discountFee = 0L;

    /**
     * Status of the sub order
     */
    private OrderStatus status = OrderStatus.Created;

    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

    private PaymentStatus payStatus = PaymentStatus.Created;

    public Long getTotalFee() {
        return this.getItemPrice() * this.getQuantity() - this.getDiscountFee();
    }

    public void setPayStatus(PaymentStatus newValue) {
        if (!newValue.equals(this.payStatus)) {
            firePropertyChange("payStatus", this.payStatus, newValue);
            this.payStatus = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }
}
