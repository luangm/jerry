package io.luan.jerry.payment.data;

import io.luan.jerry.payment.domain.Payment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PaymentDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private Long payerId;
    private Long payeeId;
    private Long channelId;
    private Long totalFee;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Integer status;

    public PaymentDO() {
        //
    }

    public PaymentDO(Payment payment) {
        this.id = payment.getId();
        this.orderId = payment.getOrderId();
        this.payerId = payment.getPayerId();
        this.payeeId = payment.getPayeeId();
        this.channelId = payment.getChannelId();
        this.totalFee = payment.getTotalFee();
        this.gmtCreate = payment.getGmtCreate();
        this.gmtModified = payment.getGmtModified();
        this.status = payment.getStatus().getValue();
    }
}
