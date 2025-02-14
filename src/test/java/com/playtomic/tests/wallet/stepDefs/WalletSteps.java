package com.playtomic.tests.wallet.stepDefs;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Transaction;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.TransactionType;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.exception.InvalidTopUpRequestException;
import com.playtomic.tests.wallet.repository.TransactionRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.request.TopUpRequest;
import com.playtomic.tests.wallet.service.*;
import fixture.PaymentDTOFixture;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @Autowired
    private TopUpService topUpService;

    @MockBean
    private StripeService stripeService;

    private WalletDTO walletServiceResponse;
    private PaymentDTO paymentServiceResponse;

    private TopUpRequest topUpRequest;

    @Given("a success charge")
    public void chargeSuccess() {
        when(stripeService.charge(any(), any())).thenReturn(new Payment(PaymentDTOFixture.PAYMENT_PROVIDER_ID));
    }

    @Given("a exception on charge")
    public void chargeException() {
        when(stripeService.charge(any(), any())).thenThrow(StripeAmountTooSmallException.class);
    }

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
        walletServiceResponse = walletService.getWalletDTOById(new ObjectId(id), false);
    }


    @When("I request to top up wallet {string} should throw exception")
    public void getWalletWithException(String id) {
        Assertions.assertThrows(InvalidTopUpRequestException.class, () -> topUpService.topUpMoney(new ObjectId(id), topUpRequest));
    }

    @When("a top up with cardNumber: {string}, expireMonth : {string}, expireYear: {string}, cvc: {string} and amount: {int}")
    public void topUpWalletRequest(String cardNumber, String expireMonth, String expireYear, String cvc, Integer amount) {
        topUpRequest = new TopUpRequest();
        topUpRequest.setCardNumber(cardNumber);
        topUpRequest.setAmount(new BigDecimal(amount));
        topUpRequest.setCvc(cvc);
        topUpRequest.setExpireMonth(expireMonth);
        topUpRequest.setExpireYear(expireYear);
    }

    @When("I request to top up wallet {string}")
    public void topUpWallet(String id) {
        paymentServiceResponse = topUpService.topUpMoney(new ObjectId(id), topUpRequest);
    }

    @When("I request the wallet with transactions with ID {string}")
    public void getWalletWithTransactions(String id) {
        walletServiceResponse = walletService.getWalletDTOById(new ObjectId(id), true);
    }

    @When("I request to create a wallet")
    public void createEmptyWallet() {
        walletServiceResponse = walletService.createEmptyWallet();
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

    @Then("I should receive success paymentResponse")
    public void verifyPaymentResponseStatus() {
        Assertions.assertEquals(TransactionStatus.SUCCESS, paymentServiceResponse.getTransactionStatus());
    }

    @Then("I should receive failed paymentResponse")
    public void verifyPaymentResponseStatusFailed() {
        Assertions.assertEquals(TransactionStatus.FAILED, paymentServiceResponse.getTransactionStatus());
    }

    @Then("I should receive a paymentId")
    public void verifyPaymentResponsePaymentId() {
        Assertions.assertNotNull(paymentServiceResponse.getId());
    }

    @Then("I shouldn't receive transactions")
    public void verifyWalletTransactions() {
        Assertions.assertNull(walletServiceResponse.getTransactions());
    }

    @Then("I should receive a null wallet")
    public void verifyNullWallet() {
        Assertions.assertNull(walletServiceResponse);
    }

    @Then("I should receive a wallet")
    public void verifyNotNullWallet() {
        Assertions.assertNotNull(walletServiceResponse);
    }

}