package com.toucan.tourtptrs.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toucan.tourtptrs.constants.EnumConstants.AccountType;
import com.toucan.tourtptrs.helper.TAmount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsDto implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = -7182413870811155931L;

	@Id
    private String id;

    private String bankName;
    
    private String accountNumber;
    
    private String branchName;
    
    private AccountType accountType;
    
    private String ifscCode;
    
    private String accountHolderName;
    
    private String mobileNumber;
    
    private String isPopular;
    
    private TAmount bankBalance;
}
