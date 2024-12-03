package com.toucan.tourtptrs.services;

import org.springframework.stereotype.Service;

import com.toucan.tourtptrs.models.Transaction_Entries;

@Service
public class TransactionsService extends AbstractCoreService<Transaction_Entries>{

	public Transaction_Entries transactions(Transaction_Entries entity) {

		switch(entity.getTypeOfTxn()) {
		
		case TOVPA_TXN:
			
		case TOMOBILENUMBER_TXN:
			
		case TOSELFTRANSFER_TXN:
			
		case TOBANK_TXN:
			
		case TOWALLET_TXN:	
			
		}
		return entity;
	}
	
}
