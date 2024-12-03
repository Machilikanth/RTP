package com.toucan.tourtpkyc.models.wallet;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toucan.tourtpkyc.constants.EnumConstants.WalletEnumsTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
	@Id
	private String id;
	
	@JsonProperty("walletId")
	private String walletId;
	@JsonProperty("walletStatus")
	private WalletEnumsTypes walletStatus;
	@JsonProperty("walletBalance")
	private double walletBalance;
}
