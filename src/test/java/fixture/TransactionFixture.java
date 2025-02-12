package fixture;

import com.playtomic.tests.wallet.dto.TransactionDTO;
import com.playtomic.tests.wallet.entity.Transaction;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.TransactionType;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransactionFixture {

    public static ObjectId TRANSACTION_ID = new ObjectId("67ab587a64e4e8293452e49c");
    public static String PAYMENT_ID = "13141516";
    public static BigDecimal AMOUNT = new BigDecimal(200);

    public static List<Transaction> buildSuccessTransactionList() {
        return List.of(buildSuccessPaymentTransaction(), buildSuccessSpendTransaction());
    }

    public static List<TransactionDTO> buildSuccessTransactionDtoList() {
        return List.of(buildSuccessPaymentTransactionDTO(), buildSuccessSpendTransactionDTO());
    }

    public static Transaction buildSuccessPaymentTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(TRANSACTION_ID);
        transaction.setWalletId(WalletFixture.WALLET_ID);
        transaction.setAmount(AMOUNT);
        transaction.setTransactionType(TransactionType.TOP_UP);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setPaymentId(PAYMENT_ID);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction buildSuccessSpendTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(TRANSACTION_ID);
        transaction.setWalletId(WalletFixture.WALLET_ID);
        transaction.setAmount(AMOUNT);
        transaction.setTransactionType(TransactionType.SPEND);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setDate(new Date());
        return transaction;
    }


    public static TransactionDTO buildSuccessPaymentTransactionDTO() {
        return TransactionDTO.builder()
                .setId(TRANSACTION_ID)
                .setWalletId(WalletFixture.WALLET_ID)
                .setAmount(AMOUNT)
                .setTransactionType(TransactionType.TOP_UP)
                .setTransactionStatus(TransactionStatus.SUCCESS)
                .setPaymentId(PAYMENT_ID)
                .setDate(new Date())
                .build();
    }

    public static TransactionDTO buildSuccessSpendTransactionDTO() {
        return TransactionDTO.builder()
                .setId(TRANSACTION_ID)
                .setWalletId(WalletFixture.WALLET_ID)
                .setAmount(AMOUNT)
                .setTransactionType(TransactionType.SPEND)
                .setTransactionStatus(TransactionStatus.SUCCESS)
                .setDate(new Date())
                .build();
    }




}
