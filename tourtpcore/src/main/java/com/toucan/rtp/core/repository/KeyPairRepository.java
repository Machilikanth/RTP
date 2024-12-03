package com.toucan.rtp.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.core.model.RSA_KEY;


@Repository
@EnableMongoRepositories
public interface KeyPairRepository extends MongoRepository<RSA_KEY, String>{

}
