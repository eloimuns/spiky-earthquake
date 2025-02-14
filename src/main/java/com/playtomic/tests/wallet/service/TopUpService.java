package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.exception.InvalidTopUpRequestException;
import com.playtomic.tests.wallet.request.TopUpRequest;
import com.playtomic.tests.wallet.utils.TransactionUtils;
import io.micrometer.common.util.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class TopUpService {

    private final Logger log = LoggerFactory.getLogger(TopUpService.class);

    private final WalletService walletService;

    private final TransactionService transactionService;

    private final PaymentService paymentService;

    @Autowired
    private TopUpService(WalletService walletService, TransactionService transactionService, PaymentService paymentService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.paymentService = paymentService;
    }

    public PaymentDTO topUpMoney(ObjectId walletId, TopUpRequest topUpRequest) {
        if (hasInvalidRequest(topUpRequest)) {
            log.error("Top up request is invalid");
            throw new InvalidTopUpRequestException("Top up request is invalid");
        }
        Optional<Wallet> walletOptional = walletService.getWalletById(walletId);
        if (walletOptional.isEmpty()) {
            log.error("Wallet not found");
            throw new InvalidTopUpRequestException("Top up request is invalid");
        }
        Wallet wallet = walletOptional.get();
        PaymentDTO paymentResponse = paymentService.executeCharge(topUpRequest);
        if (TransactionStatus.SUCCESS.equals(paymentResponse.getTransactionStatus())) {
            walletService.topUpMoney(wallet, topUpRequest.getAmount());
        }
        transactionService.createTopUpTransaction(TransactionUtils.createTransactionDTO(wallet.getId(), topUpRequest.getAmount(), paymentResponse));
        return paymentResponse;
    }

    private boolean hasInvalidRequest(TopUpRequest topUpRequest) {
        return topUpRequest == null
                || topUpRequest.getAmount() == null
                || StringUtils.isBlank(topUpRequest.getCardNumber())
                || StringUtils.isBlank(topUpRequest.getExpireMonth())
                || StringUtils.isBlank(topUpRequest.getExpireYear())
                || StringUtils.isBlank(topUpRequest.getCvc());
    }

}
