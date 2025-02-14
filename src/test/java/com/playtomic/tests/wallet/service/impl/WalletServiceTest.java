package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        Optional<Wallet> wallet = walletService.getWalletById(WalletFixture.WALLET_ID);

        assertTrue(wallet.isPresent());
        assertEquals(WalletFixture.WALLET_ID, wallet.get().getId());
        assertEquals(WalletFixture.BALANCE, wallet.get().getBalance());
    }


    @Test
    public void testGetWalletDTOById_existingWallet() {
        when(walletRepository.findById(WalletFixture.WALLET_ID))
                .thenReturn(Optional.of(WalletFixture.buildWallet()));

        WalletDTO walletDTO = walletService.getWalletDTOById(WalletFixture.WALLET_ID, false);

        assertNotNull(walletDTO);
        assertEquals(WalletFixture.WALLET_ID, walletDTO.getId());
        assertEquals(WalletFixture.BALANCE, walletDTO.getBalance());
    }

    @Test
    public void testGetWalletDTOByIdWithTransactions_existingWallet() {
        when(walletRepository.findById(WalletFixture.WALLET_ID))
                .thenReturn(Optional.of(WalletFixture.buildWallet()));
        when(transactionService.getTransactionsById(WalletFixture.WALLET_ID)).thenReturn(TransactionFixture.buildSuccessTransactionDtoList());

        WalletDTO walletDTO = walletService.getWalletDTOById(WalletFixture.WALLET_ID, true);

        assertNotNull(walletDTO);
        assertEquals(WalletFixture.WALLET_ID, walletDTO.getId());
        assertEquals(WalletFixture.BALANCE, walletDTO.getBalance());
        assertEquals(2, walletDTO.getTransactions().size());
    }

    @Test
    public void testGetWalletById_nonExistingWallet() {
        ObjectId id = new ObjectId();
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Wallet> wallet = walletService.getWalletById(id);

        assertTrue(wallet.isEmpty());
    }

    @Test
    public void testGetWalletDTOById_nonExistingWallet() {
        ObjectId id = new ObjectId();
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        WalletDTO walletDTO = walletService.getWalletDTOById(id, false);

        assertNull(walletDTO);
    }

    @Test
    public void testGetWalletDTOByIdWithTransactions_nonExistingWallet() {
        ObjectId id = new ObjectId();
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        WalletDTO walletDTO = walletService.getWalletDTOById(id, true);

        assertNull(walletDTO);
    }

    @Test
    void testTopUpMoney_ShouldIncreaseBalanceAndSaveWallet() {
        BigDecimal topUpAmount = BigDecimal.valueOf(100);
        Wallet wallet = WalletFixture.buildWallet();

        walletService.topUpMoney(wallet, topUpAmount);

        assertEquals(WalletFixture.BALANCE.add(topUpAmount), wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }
}