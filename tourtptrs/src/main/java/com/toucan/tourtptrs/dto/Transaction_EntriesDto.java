package com.toucan.tourtptrs.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toucan.tourtptrs.constants.EnumConstants.TxnType;
import com.toucan.tourtptrs.helper.TAmount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction_EntriesDto implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = 1716993218862819449L;

	@Id
	private String id;
	
	private String senderVpa; 
	
	private String senderMobileNum;
	
	private String senderWallet;
	
    private String senderAccountNumber;
    
    private String senderIfscCode;
    
    private String receiverVpa;
    
    private String receiverMobileNum;
    
    private String receiverWallet;
    
    private String receiverAccountNumber; 
    
    private String receiverIfscCode; 
    
    private TAmount txnAmount;
    
    private String txnNote;
    
    private String txnDate;
    
    private String txnTime;
    
    private String txnstatus;
    
    private String referenceId;
    
    private TxnType typeOfTxn;
	
}
