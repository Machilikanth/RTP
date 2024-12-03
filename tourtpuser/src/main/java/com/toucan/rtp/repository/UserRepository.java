package com.toucan.rtp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.model.User;

@Repository
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User, String>{

    User findByMobileNumber(String mobileNumber);
    @Query("{ 'mobileNumber': ?0, 'customVpa.customizevpa': ?1 }")
    User findByMobileNumberAndcustomizeVpa(String mobileNumber, String customizeVpa);
	User findByMobileNumberAndVpa(String mobileNumber, String vpa);


}
