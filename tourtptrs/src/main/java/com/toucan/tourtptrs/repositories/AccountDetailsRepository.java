package com.toucan.tourtptrs.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.tourtptrs.models.AccountDetails;

@Repository
@EnableMongoRepositories
public interface AccountDetailsRepository extends MongoRepository<AccountDetails, String> {
	
	@Query("{ 'userBankDetails.Vpas.vpa' : ?0 }")
	AccountDetails findbyvpa(String debitAccountVpaId);

	@Query("{ 'userBankDetails.mobileNumber' : ?0 }")
	List<AccountDetails> findbymobilenumber(String mobileNumber);
	
	
	
	@Query("{ 'userBankDetails.Vpas.vpa' : ?0 }")
	Optional<AccountDetails> findByVpa(String vpaa);

}
