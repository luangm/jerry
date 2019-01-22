package io.luan.jerry.payment.service;

import io.luan.jerry.payment.domain.Payment;

public interface PaymentService {

    Payment findById(Long id);

    Payment findByOrderId(Long orderId);

    Payment pay(Payment payment);

    Payment save(Payment payment);

}
