package com.toucan.tourtptrs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.tourtptrs.models.ListOfBanks;

@Repository
@EnableMongoRepositories
public interface ListOfBanksRepository extends MongoRepository<ListOfBanks, String>{

}
