package com.toucan.tourtptrs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.tourtptrs.models.UpiPin;

@Repository
@EnableMongoRepositories
public interface UpiPinRepository extends MongoRepository<UpiPin, String> {

	UpiPin findByAccNum(String accNum);

	UpiPin findByUpiPin(Integer pin);
	
}
