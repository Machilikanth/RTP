package com.toucan.tourtptrs.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.tourtptrs.models.BankDetails;

@Repository
@EnableMongoRepositories
public interface BankDetailsRepository extends MongoRepository<BankDetails, String>{

	List<BankDetails> findByBankName(String bankName);
	
	String findByAccountNumber(String accnumber);

	List<BankDetails> findByMobileNumber(String mobileNumber);

}
