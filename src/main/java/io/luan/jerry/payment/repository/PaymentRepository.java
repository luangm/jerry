package io.luan.jerry.payment.repository;

import io.luan.jerry.common.repository.Repository;
import io.luan.jerry.payment.domain.Payment;

public interface PaymentRepository extends Repository<Payment, Long> {

    Payment findByOrderId(Long orderId);

}
