package com.toucan.rtp.core.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.toucan.rtp.core.model.RefreshToken;
import com.toucan.rtp.core.model.User;
import com.toucan.rtp.core.repository.RefreshTokenRepository;
import com.toucan.rtp.core.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {


	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.validity}")
	private long validityInMinutes;
	

//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		if (userDetails instanceof CustomUserDetails) {
//			CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
//			claims.put("phone", customUserDetails.getMobileNumber());
//		}
//		return Jwts.builder()
//				.claims(claims)
//				.subject(userDetails.getUsername())
//				.issuedAt(Date.from(Instant.now()))
//				.expiration(Date.from(Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(validityInMinutes))))
//				.signWith(generateKey()).compact();
//	}
	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		System.out.println("in core the id is "+userDetails.getId());
		claims.put("id",userDetails.getId());
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getFirstName())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(validityInMinutes))))
				.signWith(generateKey()).compact();
	}
	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		return Keys.hmacShaKeyFor(decodedKey);
	}
	
	
	
	
	
	

//	public String extractUsername(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	public String extractMobileNumber(String token) {
//		return extractClaim(token, claims -> claims.get("phone", String.class));
//	}
//
//	public boolean isTokenValid(String token) {
//		final Claims claims = getClaims(token);
//		return !claims.getExpiration().before(Date.from(Instant.now()));
//	}
//
//	private Claims getClaims(String jwt) {
//		return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
//	}
//
//	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getClaims(token);
//		return claimsResolver.apply(claims);
//	}
}
