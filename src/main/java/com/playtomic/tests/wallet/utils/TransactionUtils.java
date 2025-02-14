package com.playtomic.tests.wallet.utils;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.entity.Transaction;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionUtils {

    public static List<TransactionDTO> convertToDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionUtils::convertToDTO)
                .collect(Collectors.toList());
    }

    public static TransactionDTO convertToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .setId(transaction.getId())
                .setObjectId(transaction.getId().toHexString())
                .setWalletId(transaction.getWalletId())
                .setWalletObjectId(transaction.getWalletId().toHexString())
                .setPaymentId(transaction.getPaymentId())
                .setDate(transaction.getDate())
                .setAmount(transaction.getAmount())
                .setTransactionType(transaction.getTransactionType())
                .setTransactionStatus(transaction.getTransactionStatus())
                .build();
    }

    public static TransactionDTO createTransactionDTO(ObjectId walletId, BigDecimal amount, PaymentDTO paymentDTO) {
        return TransactionDTO
                .builder()
                .setTransactionStatus(paymentDTO.getTransactionStatus())
                .setTransactionType(paymentDTO.getTransactionType())
                .setPaymentId(paymentDTO.getId())
                .setDate(new Date())
                .setWalletId(walletId)
                .setAmount(amount)
                .build();
    }

    public static Transaction convertToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setWalletId(transactionDTO.getWalletId());
        transaction.setPaymentId(transactionDTO.getPaymentId());
        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setTransactionStatus(transactionDTO.getTransactionStatus());
        return transaction;
    }
}
