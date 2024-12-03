package com.toucan.tourtptrs.helper;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
	private String Description;
	private String transactiontype;
	private LocalDateTime transactiondate;
	private BigDecimal amount;

}
