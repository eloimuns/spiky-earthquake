package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.exception.InvalidTopUpRequestException;
import com.playtomic.tests.wallet.request.TopUpRequest;
import com.playtomic.tests.wallet.service.PaymentService;
import com.playtomic.tests.wallet.service.TopUpService;
import com.playtomic.tests.wallet.service.TransactionService;
import com.playtomic.tests.wallet.service.WalletService;
import fixture.PaymentDTOFixture;
import fixture.TopUpRequestFixture;
import fixture.WalletFixture;
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
class TopUpServiceTest {

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private TopUpService topUpService;

    @Test
    void testTopUpMoney_InvalidRequest_ShouldReturnNull() {
        assertThrows(InvalidTopUpRequestException.class, () -> topUpService.topUpMoney(WalletFixture.WALLET_ID, new TopUpRequest()));
    }

    @Test
    void testTopUpMoney_WalletNotFound_ShouldReturnNull() {
        when(walletService.getWalletById(WalletFixture.WALLET_ID)).thenReturn(Optional.empty());
        assertThrows(InvalidTopUpRequestException.class, () -> topUpService.topUpMoney(WalletFixture.WALLET_ID, TopUpRequestFixture.buildTopUpRequest()));

    }

    @Test
    void testTopUpMoney_PaymentSuccess_ShouldTopUpWallet() {
        TopUpRequest topUpRequest = TopUpRequestFixture.buildTopUpRequest();
        when(walletService.getWalletById(WalletFixture.WALLET_ID)).thenReturn(Optional.of(WalletFixture.buildWallet()));
        when(paymentService.executeCharge(topUpRequest)).thenReturn(PaymentDTOFixture.buildSuccessPaymentDTO());

        PaymentDTO result = topUpService.topUpMoney(WalletFixture.WALLET_ID, topUpRequest);

        assertNotNull(result);
        assertEquals(TransactionStatus.SUCCESS, result.getTransactionStatus());
        verify(walletService, times(1)).topUpMoney(WalletFixture.buildWallet(), topUpRequest.getAmount());
        verify(transactionService, times(1)).createTopUpTransaction(any(TransactionDTO.class));
    }

    @Test
    void testTopUpMoney_PaymentFailed_ShouldNotTopUpWallet() {
        TopUpRequest topUpRequest = TopUpRequestFixture.buildTopUpRequest();
        when(walletService.getWalletById(WalletFixture.WALLET_ID)).thenReturn(Optional.of(WalletFixture.buildWallet()));
        when(paymentService.executeCharge(topUpRequest)).thenReturn(PaymentDTOFixture.buildFailedPaymentDTO());

        PaymentDTO result = topUpService.topUpMoney(WalletFixture.WALLET_ID, topUpRequest);

        assertNotNull(result);
        assertEquals(TransactionStatus.FAILED, result.getTransactionStatus());
        verify(walletService, never()).topUpMoney(any(Wallet.class), any(BigDecimal.class));
        verify(transactionService, times(1)).createTopUpTransaction(any(TransactionDTO.class));
    }
}
