package com.toucan.tourtptrs.helper;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkBankAccount implements Serializable{

	private String mobileNumber;
	
	private String bankName;
	
	private String userId;
}
