package com.toucan.rtp.core.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.toucan.rtp.core.model.Otp;

@Repository
@EnableMongoRepositories
public interface OtpRepository extends MongoRepository<Otp, String> {
    Optional<Otp> findByPhoneNumberAndOtp(String phoneNumber, String otp1);
    void deleteByExpirationTimeBefore(LocalDateTime dateTime);
    Optional<Otp> findByPhoneNumber(String phoneNumber);
}
