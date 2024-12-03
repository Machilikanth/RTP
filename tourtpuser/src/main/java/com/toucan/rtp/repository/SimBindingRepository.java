package com.toucan.rtp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.model.SimBinding;


@Repository
@EnableMongoRepositories
public interface SimBindingRepository extends MongoRepository<SimBinding, String>{
	
     SimBinding findByMSISDN(String MSISDN);

}
