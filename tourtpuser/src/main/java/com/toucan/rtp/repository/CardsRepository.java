package com.toucan.rtp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.model.Cards;

@Repository
public interface CardsRepository extends MongoRepository<Cards, String>{

}
