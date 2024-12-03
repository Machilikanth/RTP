package com.toucan.tourtptrs.models;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toucan.tourtptrs.constants.EnumConstants.WalletEnumsTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet implements Serializable{
	
	@Id
	private String id;
	
	@JsonProperty("walletId")
	private String walletId;
	
	@JsonProperty("walletStatus")
	private WalletEnumsTypes walletStatus;
	
	@JsonProperty("walletBalance")
	private BigDecimal walletBalance;
}