package com.toucan.rtp.gt.util;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.toucan.rtp.gt.globalexception.InvalidTokenException;
import com.toucan.rtp.gt.repository.GateRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret}")
	private String secret;
	@Autowired
	private GateRepo gateRepo;

    public Mono<Boolean> isTokenValid(String token) {
        return Mono.fromSupplier(() -> getClaims(token))
            .map(claims -> {
            	 String id = (String) claims.get("id");
            	 boolean isPresent = gateRepo.findById(id).isPresent();
            	 if (!isPresent) {
                     logger.warn("user ID does not exist in the database.");
                     return false;
                 }
                if (claims.getExpiration().before(Date.from(Instant.now()))) {
                    logger.warn("Token has expired.");
                    throw new InvalidTokenException("Token has expired.");
                }
                return true;
            })
            .onErrorResume(InvalidTokenException.class, e -> {
                logger.warn("Token validation failed: {}", e.getMessage());
                return Mono.just(false);
            })
            .onErrorResume(e -> {
                logger.error("Token validation error", e);
                return Mono.error(new InvalidTokenException("Invalid token.", e));
            });
    }
	
	private Claims getClaims(String jwt) {
		return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
	}

	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		return Keys.hmacShaKeyFor(decodedKey);
	}

}
