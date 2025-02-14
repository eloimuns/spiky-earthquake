package fixture;

import com.playtomic.tests.wallet.request.TopUpRequest;

import java.math.BigDecimal;

public class TopUpRequestFixture {

    public static TopUpRequest buildTopUpRequest() {
        TopUpRequest topUpRequest = new TopUpRequest();
        topUpRequest = new TopUpRequest();
        topUpRequest.setAmount(BigDecimal.valueOf(100));
        topUpRequest.setCardNumber("1234567812345678");
        topUpRequest.setExpireMonth("12");
        topUpRequest.setExpireYear("26");
        topUpRequest.setCvc("123");
        return topUpRequest;
    }
}
