package com.playtomic.tests.wallet.dto;

import com.playtomic.tests.wallet.entity.ErrorType;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.TransactionType;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder(setterPrefix = "set")
public class PaymentDTO {

    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String id;
    private ErrorType errorType;
}
