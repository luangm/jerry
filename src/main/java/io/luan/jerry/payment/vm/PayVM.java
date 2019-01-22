package io.luan.jerry.payment.vm;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayVM implements Serializable {

    private Long paymentId;

    private Long orderId;

    private String password;

    private String action;

}
