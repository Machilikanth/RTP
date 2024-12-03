package com.toucan.tourtptrs.models.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionWalletRequest {
	@NotNull(message="userId is required")
	private String userId;
	
	@NotNull(message="amount is required")
	private BigDecimal amount;
	
	@NotNull(message="accountNumber is required")
	private String accountNumber;
	
	@NotNull(message="accId is required")
	private String accountsId;
	
	@NotNull(message="pin is required")
	private Integer pin;	
}
