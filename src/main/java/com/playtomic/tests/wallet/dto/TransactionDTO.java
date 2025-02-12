package com.playtomic.tests.wallet.dto;

import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.TransactionType;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder(setterPrefix = "set")
public class TransactionDTO {
    private ObjectId id;
    private ObjectId walletId;
    private String paymentId;
    private BigDecimal amount;
    private Date date;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
}
