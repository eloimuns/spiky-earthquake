package com.playtomic.tests.wallet.stepDefs;

import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Transaction;
import com.playtomic.tests.wallet.entity.TransactionType;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.repository.TransactionRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletSteps {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletService walletService;

    private WalletDTO walletServiceResponse;

    @Given("a wallet with ID {string} and balance {long} with transactions {int}")
    public void createWallet(String id, Long balance, Integer numTransactions) {
        Wallet wallet = new Wallet();
        wallet.setId(new ObjectId(id));
        wallet.setBalance(new BigDecimal(balance));
        walletRepository.save(wallet);
        createTransactions(wallet.getId(), numTransactions);
    }

    private void createTransactions(ObjectId walletId, Integer numTransactions) {
        List<Transaction> transactionList = new ArrayList<>();
        for (int i = 0; i < numTransactions; i++) {
            Transaction transaction = new Transaction();
            transaction.setId(new ObjectId());
            transaction.setWalletId(walletId);
            transaction.setTransactionType(TransactionType.TOP_UP);
            transactionList.add(transaction);
        }
        transactionRepository.saveAll(transactionList);
    }

    @When("I request the wallet with ID {string}")
    public void getWallet(String id) {
        walletServiceResponse = walletService.getWalletById(new ObjectId(id), false);
    }

    @When("I request the wallet with transactions with ID {string}")
    public void getWalletWithTransactions(String id) {
        walletServiceResponse = walletService.getWalletById(new ObjectId(id), true);
    }

    @Then("I should receive a wallet with ID {string} and balance {long}")
    public void verifyWallet(String id, Long balance) {
        Assertions.assertNotNull(walletServiceResponse);
        Assertions.assertEquals(new ObjectId(id), walletServiceResponse.getId());
        Assertions.assertEquals(new BigDecimal(balance), walletServiceResponse.getBalance());
    }

    @Then("I should receive {int} transactions")
    public void verifyWalletTransactions(Integer numTransactions) {
        Assertions.assertEquals(numTransactions, walletServiceResponse.getTransactions().size());
    }

    @Then("I shouldn't receive transactions")
    public void verifyWalletTransactions() {
        Assertions.assertNull(walletServiceResponse.getTransactions());
    }

    @Then("I should receive a null wallet")
    public void verifyNullWallet() {
        Assertions.assertNull(walletServiceResponse);
    }
}