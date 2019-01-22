package io.luan.jerry.payment.factory;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.payment.data.PaymentDO;
import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.payment.domain.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {

    public Payment load(PaymentDO paymentDO) {
        var payment = new Payment();
        payment.setId(paymentDO.getId());
        payment.setOrderId(paymentDO.getOrderId());
        payment.setPayerId(paymentDO.getPayerId());
        payment.setPayeeId(paymentDO.getPayeeId());
        payment.setChannelId(paymentDO.getChannelId());
        payment.setTotalFee(paymentDO.getTotalFee());
        payment.setGmtCreate(paymentDO.getGmtCreate());
        payment.setGmtModified(paymentDO.getGmtModified());
        payment.setState(EntityState.Unchanged);
        payment.setStatus(PaymentStatus.fromValue(paymentDO.getStatus()));
        return payment;
    }
}
