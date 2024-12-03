package com.toucan.tourtptrs.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.toucan.tourtptrs.constants.EnumConstants.AccountType;
import com.toucan.tourtptrs.helper.TAmount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("BankDetails")
public class BankDetails implements Serializable{

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
