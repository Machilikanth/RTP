package com.toucan.tourtpkyc.utils;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.toucan.tourtpkyc.constants.EnumConstants.WalletEnumsTypes;
import com.toucan.tourtpkyc.models.wallet.Wallet;


@Service
public class RepoHelpers {
	public  Wallet saveWalletStatus(String userId) {
		Wallet walletDetails = new Wallet();
		
		walletDetails.setId(userId);
		walletDetails.setWalletStatus(WalletEnumsTypes.ACTIVE);
		walletDetails.setWalletId(UUID.randomUUID().toString());
		return walletDetails;
	}
}
