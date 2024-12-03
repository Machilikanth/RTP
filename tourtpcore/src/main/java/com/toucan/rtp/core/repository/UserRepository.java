package com.toucan.rtp.core.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.core.model.User;

@Repository
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User, String>{

  Optional< User> findByMobileNumber(String mobileNumber);
   

}
