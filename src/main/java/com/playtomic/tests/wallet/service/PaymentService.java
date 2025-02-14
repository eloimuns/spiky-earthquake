package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.entity.ErrorType;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.TransactionType;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.request.TopUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    private final StripeService stripeService;

    @Autowired
    private PaymentService(WalletRepository walletRepository, TransactionService transactionService, StripeService stripeService) {
        this.stripeService = stripeService;
    }

    public PaymentDTO executeCharge(TopUpRequest topUpRequest) {
        try {
            Payment payment = stripeService.charge(topUpRequest.getCardNumber(), topUpRequest.getAmount());
            return PaymentDTO
                    .builder()
                    .setId(payment.getId())
                    .setTransactionStatus(TransactionStatus.SUCCESS)
                    .setTransactionType(TransactionType.TOP_UP)
                    .build();
        } catch (StripeAmountTooSmallException exception) {
            return PaymentDTO
                    .builder()
                    .setTransactionStatus(TransactionStatus.FAILED)
                    .setErrorType(ErrorType.AMOUNT_TOO_LOW)
                    .setTransactionType(TransactionType.TOP_UP)
                    .build();
        } catch (StripeServiceException exception) {
            return PaymentDTO
                    .builder()
                    .setTransactionStatus(TransactionStatus.FAILED)
                    .setErrorType(ErrorType.STRIPE_EXCEPTION)
                    .setTransactionType(TransactionType.TOP_UP)
                    .build();
        }
    }

    public PaymentDTO executeRefund(String paymentId) {
        try {
            stripeService.refund(paymentId);
            return PaymentDTO
                    .builder()
                    .setTransactionStatus(TransactionStatus.SUCCESS)
                    .setTransactionType(TransactionType.REFUND)
                    .build();
        } catch (StripeAmountTooSmallException exception) {
            return PaymentDTO
                    .builder()
                    .setTransactionStatus(TransactionStatus.FAILED)
                    .setTransactionType(TransactionType.REFUND)
                    .setErrorType(ErrorType.AMOUNT_TOO_LOW)
                    .build();
        } catch (StripeServiceException exception) {
            return PaymentDTO
                    .builder()
                    .setTransactionStatus(TransactionStatus.FAILED)
                    .setTransactionType(TransactionType.REFUND)
                    .setErrorType(ErrorType.STRIPE_EXCEPTION)
                    .build();
        }
    }
}
