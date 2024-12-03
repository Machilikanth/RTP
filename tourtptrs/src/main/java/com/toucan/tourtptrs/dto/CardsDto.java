package com.toucan.tourtptrs.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardsDto implements Serializable{

	private String id;
	private String cardnumber;
	private String cvv;
	private String expiredate;
}
