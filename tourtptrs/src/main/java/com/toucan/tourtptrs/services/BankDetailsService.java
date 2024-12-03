package com.toucan.tourtptrs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.toucan.tourtptrs.constants.EnumConstants.EntityTypeEnum;
import com.toucan.tourtptrs.dto.BankDetailsDto;
import com.toucan.tourtptrs.models.BankDetails;
import com.toucan.tourtptrs.repositories.BankDetailsRepository;

@Service
public class BankDetailsService extends AbstractCoreService<BankDetails>{
	
	@Autowired
	private BankDetailsRepository bankDetailsRepository;

	@Override
	public Class<BankDetails> getEntityClass() {
		return BankDetails.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new BankDetailsDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new BankDetails();
	}

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.BANKDETAILS;
	}
	
	@Override
	public BankDetails create(BankDetails entity) {
		List<BankDetails> banksbymobilenum = bankDetailsRepository.findByMobileNumber(entity.getMobileNumber());
		for(BankDetails details : banksbymobilenum) {
			if(details.getMobileNumber()==null) {
				if (!isAccountNumberUnique(entity.getAccountNumber())) {
					throw new IllegalArgumentException("Account number " + entity.getAccountNumber() + " already exists.");
         }else if (!isBankNameUnique(entity.getBankName())) {
             throw new IllegalArgumentException("BankName " + entity.getBankName() + " already exists.");
         } else {
			return super.create(entity);
		}
	}
		}
			if(!isAccountNumberUnique(entity.getAccountNumber())) {
				throw new RuntimeException("Account number should be unique");
			 }else {
				 return super.create(entity);
			 }
		}
	
	public boolean isAccountNumberUnique(String accountNumber) {
        Query query = new Query(Criteria.where("accountNumber").is(accountNumber));
        List<BankDetails> accountNum = mongoTemplate.find(query, BankDetails.class);
        return accountNum.isEmpty();
    }
	
	public boolean isBankNameUnique(String bankname) {
        Query query = new Query(Criteria.where("bankName").is(bankname));
        List<BankDetails> bankName = mongoTemplate.find(query, BankDetails.class);
        return bankName.isEmpty();
    }
	
}
