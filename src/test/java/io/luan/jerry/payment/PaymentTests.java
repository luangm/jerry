package io.luan.jerry.payment;

import io.luan.jerry.payment.data.PaymentMapper;
import io.luan.jerry.payment.domain.Payment;
import io.luan.jerry.payment.repository.PaymentRepository;
import io.luan.jerry.payment.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Test
    public void repoSave() {
        Long orderId = System.currentTimeMillis() / 1000;
        var payment = new Payment();
        payment.setOrderId(orderId);
        payment.setChannelId(1L);
        payment.setPayerId(222L);
        payment.setPayeeId(333L);
        payment.setTotalFee(999L);

        paymentService.save(payment);
    }
    
}
