package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Transaction;
import com.playtomic.tests.wallet.repository.TransactionRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.TransactionService;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.utils.TransactionUtils;
import fixture.TransactionFixture;
import fixture.WalletFixture;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGetTransactionsByWalletId_existingTransactions() {
        when(transactionRepository.findByWalletId(WalletFixture.WALLET_ID)).thenReturn(TransactionFixture.buildSuccessTransactionList());

        List<TransactionDTO> result = transactionService.getTransactionsById(WalletFixture.WALLET_ID);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findByWalletId(WalletFixture.WALLET_ID);
    }

    @Test
    void testGetTransactionsByWalletId_noTransactions() {
        when(transactionRepository.findByWalletId(WalletFixture.WALLET_ID)).thenReturn(List.of());

        List<TransactionDTO> result = transactionService.getTransactionsById(WalletFixture.WALLET_ID);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(transactionRepository, times(1)).findByWalletId(WalletFixture.WALLET_ID);
    }

    @Test
    void testCreateTopUpTransaction_ShouldSaveTransaction() {
        Transaction transactionEntity = TransactionFixture.buildSuccessPaymentTransaction();
        transactionService.createTopUpTransaction(TransactionUtils.convertToDTO(transactionEntity));

        verify(transactionRepository, times(1)).save(transactionEntity);
    }
}