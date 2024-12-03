package com.toucan.rtp.gt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.gt.model.GatePass;


@Repository
@EnableMongoRepositories
public interface GateRepo extends MongoRepository<GatePass, String>{

}