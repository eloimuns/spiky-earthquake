package fixture;

import com.playtomic.tests.wallet.dto.PaymentDTO;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.ErrorType;
import com.playtomic.tests.wallet.entity.TransactionStatus;
import com.playtomic.tests.wallet.entity.TransactionType;
import com.playtomic.tests.wallet.entity.Wallet;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class PaymentDTOFixture {

    public static String PAYMENT_PROVIDER_ID = "ecd09833-cb34-4f9d-a24d-d6deee3d0e09";

    public static PaymentDTO buildSuccessPaymentDTO() {
        return PaymentDTO
                .builder()
                .setTransactionStatus(TransactionStatus.SUCCESS)
                .setTransactionType(TransactionType.TOP_UP)
                .setId(PAYMENT_PROVIDER_ID)
                .build();
    }

    public static PaymentDTO buildFailedPaymentDTO() {
        return PaymentDTO
                .builder()
                .setTransactionStatus(TransactionStatus.FAILED)
                .setTransactionType(TransactionType.TOP_UP)
                .setErrorType(ErrorType.AMOUNT_TOO_LOW)
                .build();
    }
}
