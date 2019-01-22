package io.luan.jerry.payment.service.impl;

import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.payment.domain.PaymentStatus;
import io.luan.jerry.payment.repository.PaymentRepository;
import io.luan.jerry.payment.service.PaymentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    @Override
    public Payment pay(Payment payment) {
        payment.setStatus(PaymentStatus.Paid);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment save(Payment payment) {
        paymentRepository.save(payment);
        return payment;
    }

}
