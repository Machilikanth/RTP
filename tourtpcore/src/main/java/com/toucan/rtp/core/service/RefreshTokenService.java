package com.toucan.rtp.core.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toucan.rtp.core.model.RefreshToken;
import com.toucan.rtp.core.model.User;
import com.toucan.rtp.core.repository.RefreshTokenRepository;
import com.toucan.rtp.core.repository.UserRepository;


@Service
public class RefreshTokenService {

	 @Autowired
	    private RefreshTokenRepository refreshTokenRepository;
	    @Autowired
	    private UserRepository userRepository;

	    public RefreshToken createRefreshToken(User userDetails) {
	        RefreshToken refreshToken = RefreshToken.builder()
	                .user(userRepository.findByMobileNumber(userDetails.getMobileNumber()).get())
	                .token(UUID.randomUUID().toString())
	                .expiryDate(Instant.now().plusMillis((long) 1.66667e-5))//10
	                .build();
	        System.out.println("The Refresh Token in RefreshTokenService "+ Instant.now()+""+refreshToken);
	        return refreshTokenRepository.save(refreshToken);
	    }


	    public Optional<RefreshToken> findByToken(String token) {
	    	System.out.println("Querying database for token: " + token);
	        return refreshTokenRepository.findByToken(token);
	    }


	    public RefreshToken verifyExpiration(RefreshToken token) {
	        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
	            refreshTokenRepository.delete(token);
	            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
	        }
	        return token;
	    }

	}