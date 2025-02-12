package com.playtomic.tests.wallet.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "wallets")
@Data
public class Wallet {

    @Id
    private ObjectId id;
    private BigDecimal balance;
}