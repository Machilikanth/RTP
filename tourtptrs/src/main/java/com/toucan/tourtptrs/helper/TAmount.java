package com.toucan.tourtptrs.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TAmount implements Serializable{

private BigDecimal value;
	
	private Currency code;
	
}
