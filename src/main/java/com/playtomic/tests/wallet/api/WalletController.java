package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.service.WalletService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final Logger log = LoggerFactory.getLogger(WalletController.class);

    private final WalletService walletService;

    @Autowired

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable ObjectId walletId) {
        log.info("Get wallet by id: " + walletId.toString());
        WalletDTO wallet = walletService.getWalletById(walletId, false);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @GetMapping("/wallet/{walletId}/detail")
    public ResponseEntity<WalletDTO> getWalletWithTransactions(@PathVariable ObjectId walletId) {
        log.info("Get wallet by id: " + walletId.toString());
        WalletDTO wallet = walletService.getWalletById(walletId, true);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }
}
