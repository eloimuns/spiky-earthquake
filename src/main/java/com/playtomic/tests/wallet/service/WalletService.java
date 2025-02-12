package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.utils.WalletDTOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public WalletDTO getWalletById(ObjectId walletId, boolean getTransactions) {
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);
        if (walletOptional.isEmpty()) {
            return null;
        }
        WalletDTO walletDTO = WalletDTOUtils.convertToDTO(walletOptional.get());
        if (getTransactions) {
            List<TransactionDTO> transactions = transactionService.getTransactionsById(walletId);
            walletDTO.setTransactions(transactions);
        }
        return walletDTO;
    }

}
