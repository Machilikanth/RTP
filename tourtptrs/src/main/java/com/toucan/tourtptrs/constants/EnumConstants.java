package com.toucan.tourtptrs.constants;

public class EnumConstants {
	
	public enum AccountType{
		SAVINGS, CURRENT
	}
	
	public enum EntityTypeEnum{
		BANKDETAILS, ACCOUNTDETAILS, UPIPIN, LISTOFBANKS,CARDS
	}
	
	public enum SortOrderEnum{
		ASC,DESC
	}
	
	public enum UserStatusEnum{
		ACTIVE,PENDING
	}
	
	public enum TermsandConditionEnum{
		YES,NO
	}
public enum vpaStatusEnum{
	ACTIVATE,DEACTIVATE
}
	
	
	public enum WalletEnumsTypes {
		ACTIVE, INACTIVE
	}
	
	public enum TransactionStatus {
		SUCCESS, FAILURE
	}
	
	public enum TxnType{
		TOSELFTRANSFER_TXN, TOVPA_TXN, TOBANK_TXN, TOMOBILENUMBER_TXN, TOWALLET_TXN 
	}
}

