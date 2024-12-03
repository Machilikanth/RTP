package com.toucan.tourtptrs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.toucan.tourtptrs.models.Wallet;

public interface WalletRepository extends MongoRepository<Wallet, String> {
}
