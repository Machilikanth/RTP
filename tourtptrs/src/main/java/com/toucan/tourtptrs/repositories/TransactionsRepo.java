package com.toucan.tourtptrs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.toucan.tourtptrs.models.response.TransactionResponse;

public interface TransactionsRepo extends MongoRepository<TransactionResponse, String> {

}
