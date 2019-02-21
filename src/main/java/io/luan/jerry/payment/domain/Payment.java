package io.luan.jerry.payment.domain;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.common.domain.EntityState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Payment extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Payment ID
     */
    private Long id;

    /**
     * Order ID
     */
    private Long orderId;

    /**
     * Payer ID
     */
    private Long payerId;

    /**
     * Payee ID
     */
    private Long payeeId;

    /**
     * Payment Channel
     */
    private Long channelId;

    /**
     * Total Fee, typically same as Order.totalFee
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
     * Status
     */
    private PaymentState status = PaymentState.Created;

    public Payment() {
        this.setState(EntityState.Added);
    }

    public void setStatus(PaymentState newValue) {
        if (!newValue.equals(this.status)) {
            firePropertyChange("status", this.status, newValue);
            this.status = newValue;
            this.gmtModified = LocalDateTime.now().withNano(0);
        }
    }

}