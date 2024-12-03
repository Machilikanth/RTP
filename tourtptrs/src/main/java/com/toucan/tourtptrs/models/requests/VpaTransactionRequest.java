package com.toucan.tourtptrs.models.requests;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.toucan.tourtptrs.helper.Transactions;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VpaTransactionRequest {

	@Id
	private String id;

//	@NotNull(message="userId is required")
//	private String userId;

	@NotNull(message = "credit vpa id is required")
	private String creditAccountVpaId;
	private String accountsId;
	@NotNull(message = "debit vpa id is required")
	private String debitAccountVpaId;

	@NotNull(message = "pin is required")
	private Integer pin;

	private ArrayList<Transactions> transaction;
}
