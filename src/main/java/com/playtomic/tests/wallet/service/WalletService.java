package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.request.TopUpRequest;
import com.playtomic.tests.wallet.utils.WalletUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    private final TransactionService transactionService;


    @Autowired
    private WalletService(WalletRepository walletRepository, TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    public Optional<Wallet> getWalletById(ObjectId walletId) {
        return walletRepository.findById(walletId);
    }

    public WalletDTO createEmptyWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal(0));
        return WalletUtils.convertToDTO(walletRepository.save(wallet));
    }

    public WalletDTO getWalletDTOById(ObjectId walletId, boolean getTransactions) {
        Optional<Wallet> walletOptional = getWalletById(walletId);
        if (walletOptional.isEmpty()) {
            return null;
        }
        WalletDTO walletDTO = WalletUtils.convertToDTO(walletOptional.get());
        if (getTransactions) {
            List<TransactionDTO> transactions = transactionService.getTransactionsById(walletId);
            walletDTO.setTransactions(transactions);
        }
        return walletDTO;
    }

    public void topUpMoney(Wallet wallet, BigDecimal amount) {
        wallet.addBalance(amount);
        walletRepository.save(wallet);
    }

}
