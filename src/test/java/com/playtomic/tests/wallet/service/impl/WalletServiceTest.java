package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.TransactionService;
import com.playtomic.tests.wallet.service.WalletService;
import fixture.TransactionFixture;
import fixture.WalletFixture;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private WalletService walletService;

    @Test
    public void testGetWalletById_existingWallet() {
        when(walletRepository.findById(WalletFixture.WALLET_ID))
                .thenReturn(Optional.of(WalletFixture.buildWallet()));

        WalletDTO walletDTO = walletService.getWalletById(WalletFixture.WALLET_ID, false);

        assertNotNull(walletDTO);
        assertEquals(WalletFixture.WALLET_ID, walletDTO.getId());
        assertEquals(WalletFixture.BALANCE, walletDTO.getBalance());
    }

    @Test
    public void testGetWalletByIdWithTransactions_existingWallet() {
        when(walletRepository.findById(WalletFixture.WALLET_ID))
                .thenReturn(Optional.of(WalletFixture.buildWallet()));
        when(transactionService.getTransactionsById(WalletFixture.WALLET_ID)).thenReturn(TransactionFixture.buildSuccessTransactionDtoList());

        WalletDTO walletDTO = walletService.getWalletById(WalletFixture.WALLET_ID, true);

        assertNotNull(walletDTO);
        assertEquals(WalletFixture.WALLET_ID, walletDTO.getId());
        assertEquals(WalletFixture.BALANCE, walletDTO.getBalance());
        assertEquals(2, walletDTO.getTransactions().size());
    }

    @Test
    public void testGetWalletById_nonExistingWallet() {
        ObjectId id = new ObjectId();
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        WalletDTO walletDTO = walletService.getWalletById(id, false);

        assertNull(walletDTO);
    }

    @Test
    public void testGetWalletByIdWithTransactions_nonExistingWallet() {
        ObjectId id = new ObjectId();
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        WalletDTO walletDTO = walletService.getWalletById(id, true);

        assertNull(walletDTO);
    }
}