package fixture;

import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class WalletFixture {

    public static String WALLET_ID_STRING = "67ab587a64e4e8293452e49c";
    public static ObjectId WALLET_ID = new ObjectId("67ab587a64e4e8293452e49c");
    public static BigDecimal BALANCE = new BigDecimal(200);

    public static Wallet buildWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(WALLET_ID);
        wallet.setBalance(BALANCE);
        return wallet;
    }

    public static WalletDTO buildWalletDTO() {
        return WalletDTO.builder()
                .setId(WALLET_ID)
                .setBalance(BALANCE)
                .build();
    }
}
