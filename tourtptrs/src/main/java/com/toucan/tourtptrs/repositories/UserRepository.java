package com.toucan.tourtptrs.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.tourtptrs.helper.UPIID;
import com.toucan.tourtptrs.models.User;

@Repository
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User, String>{


	Optional<User> findByMobileNumber(String mobileNumber);

	@Query("{'Vpas.vpa': ?0}")
	Optional<User> findByVpa(String vpaa);

}
