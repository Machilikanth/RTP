package com.toucan.tourtptrs.helper;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toucan.tourtptrs.constants.EnumConstants.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBankDetails implements Serializable{

	 @JsonIgnore
	private static final long serialVersionUID = -6496952823999680616L;

		private String bankName;
	    
	    private String accountNumber;
	    
	    private String branchName;
	    
	    private AccountType accountType;
	    
	    private String ifscCode;
	    
	    private String accountHolderName;
	    
	    private String mobileNumber;
	    
	    private String isPopular;
	    
	    private TAmount bankBalance;
	    
	    private  ArrayList<UPIID> vpas;
	    
	    private ArrayList<Transactions> transaction;
	
}
