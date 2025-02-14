package com.playtomic.tests.wallet.utils;

import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;

public class WalletUtils {

    public static WalletDTO convertToDTO(Wallet wallet) {
        return WalletDTO.builder()
                .setId(wallet.getId())
                .setBalance(wallet.getBalance())
                .setObjectId(wallet.getId().toHexString())
                .build();
    }
}
