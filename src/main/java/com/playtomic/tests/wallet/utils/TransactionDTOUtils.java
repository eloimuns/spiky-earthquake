package com.playtomic.tests.wallet.utils;

import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionDTOUtils {

    public static List<TransactionDTO> convertToDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionDTOUtils::convertToDTO)
                .collect(Collectors.toList());
    }

    public static TransactionDTO convertToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .setId(transaction.getId())
                .setWalletId(transaction.getWalletId())
                .setPaymentId(transaction.getPaymentId())
                .setDate(transaction.getDate())
                .setAmount(transaction.getAmount())
                .setTransactionType(transaction.getTransactionType())
                .setTransactionStatus(transaction.getTransactionStatus())
                .build();
    }
}
