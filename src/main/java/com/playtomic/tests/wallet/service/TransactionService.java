package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.repository.TransactionRepository;
import com.playtomic.tests.wallet.utils.TransactionUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    private TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getTransactionsById(ObjectId walletId) {
        return TransactionUtils.convertToDTOList(transactionRepository.findByWalletId(walletId));
    }

    public void createTopUpTransaction(TransactionDTO transactionDTO) {
        transactionRepository.save(TransactionUtils.convertToEntity(transactionDTO));
    }

}
