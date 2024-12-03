package com.toucan.rtp.dto;

import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardsDto {
	
	private String id;
	private String cardnumber;
	private String cvv;
	private String expiredate;

}
