package com.playtomic.tests.wallet.dto;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder(setterPrefix = "set")
public class WalletDTO {

    private ObjectId id;
    private String objectId;
    private BigDecimal balance;
    private List<TransactionDTO> transactions;
}
