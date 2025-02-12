package com.playtomic.tests.wallet.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "transactions")
@Data
public class Transaction {
    private ObjectId id;
    private ObjectId walletId;
    private String paymentId;
    private BigDecimal amount;
    private Date date;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;

}
