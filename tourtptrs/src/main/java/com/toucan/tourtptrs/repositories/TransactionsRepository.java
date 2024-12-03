package com.toucan.tourtptrs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.tourtptrs.models.Transaction_Entries;

@Repository
@EnableMongoRepositories
public interface TransactionsRepository extends MongoRepository<Transaction_Entries, String>{

}
