package com.playtomic.tests.wallet.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpRequest {
    private BigDecimal amount;
    private String cardNumber;
    private String expireMonth;
    private String expireYear;
    private String cvc;
}
