package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.exception.InvalidTopUpRequestException;
import com.playtomic.tests.wallet.request.TopUpRequest;
import com.playtomic.tests.wallet.service.TopUpService;
import com.playtomic.tests.wallet.service.WalletService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

    private final Logger log = LoggerFactory.getLogger(WalletController.class);

    private final WalletService walletService;
    private final TopUpService topUpService;

    @Autowired

    public WalletController(WalletService walletService, TopUpService topUpService) {
        this.walletService = walletService;
        this.topUpService = topUpService;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletDTO> createEmptyWallet() {
        log.info("Create Empty Wallet");
        WalletDTO wallet = walletService.createEmptyWallet();
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable ObjectId walletId) {
        log.info("Get wallet by id: " + walletId.toString());
        WalletDTO wallet = walletService.getWalletDTOById(walletId, false);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @GetMapping("/wallet/{walletId}/detail")
    public ResponseEntity<WalletDTO> getWalletWithTransactions(@PathVariable ObjectId walletId) {
        log.info("Get wallet by id: " + walletId.toString());
        WalletDTO wallet = walletService.getWalletDTOById(walletId, true);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @PutMapping("/wallet/{walletId}/top-up")
    public ResponseEntity<PaymentDTO> topUpWalletAmount(@PathVariable ObjectId walletId, @RequestBody TopUpRequest topUpRequest) {
        log.info("Top up wallet amount with wallt id: " + walletId.toString());
        try {
            PaymentDTO paymentResponse = topUpService.topUpMoney(walletId, topUpRequest);
            return paymentResponse != null ? ResponseEntity.ok(paymentResponse) : ResponseEntity.notFound().build();
        } catch (InvalidTopUpRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
