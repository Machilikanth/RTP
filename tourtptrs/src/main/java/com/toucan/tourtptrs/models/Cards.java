package com.toucan.tourtptrs.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cards implements Serializable{

	private static final long serialVersionUID = 8078593642601593275L;
	@Id
	private String id;
	private String cardnumber;
	@Transient
	private String cvv;
	private String expiredate;
	
}
