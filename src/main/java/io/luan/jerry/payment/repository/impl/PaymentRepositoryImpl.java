package io.luan.jerry.payment.repository.impl;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.payment.data.PaymentDO;
import io.luan.jerry.payment.data.PaymentMapper;
import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.payment.factory.PaymentFactory;
import io.luan.jerry.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentFactory paymentFactory;

    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentRepositoryImpl(PaymentFactory paymentFactory, PaymentMapper paymentMapper) {
        this.paymentFactory = paymentFactory;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public boolean delete(Payment payment) {
        var paymentDO = new PaymentDO(payment);
        if (paymentDO.getId() != null) {
            int count = paymentMapper.delete(paymentDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        for (PaymentDO paymentDO : paymentMapper.findAll()) {
            payments.add(paymentFactory.load(paymentDO));
        }
        return payments;
    }

    @Override
    public Payment findById(Long id) {
        var paymentDO = paymentMapper.findById(id);
        return paymentFactory.load(paymentDO);
    }

    @Override
    public void save(Payment payment) {
        var paymentDO = new PaymentDO(payment);
        switch (payment.getState()) {
            case Added:
            case Detached:
                paymentMapper.insert(paymentDO);
                payment.setId(paymentDO.getId());
                break;
            case Modified:
                paymentMapper.update(paymentDO);
                break;
        }
        payment.setState(EntityState.Unchanged);
    }

    @Override
    public Payment findByOrderId(Long orderId) {
        var paymentDO = paymentMapper.findByOrderId(orderId);
        return paymentFactory.load(paymentDO);
    }
}
