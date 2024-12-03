package com.toucan.tourtpkyc.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.toucan.tourtpkyc.models.wallet.Wallet;

public interface WalletRepo extends MongoRepository<Wallet, String> {

}
