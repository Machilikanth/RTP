package com.toucan.rtp.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "otps")
public class Otp implements Serializable{

	private static final long serialVersionUID = -6645236490076903184L;
	@Id
    private String id; 
    @EncryptedField
    private String phoneNumber;
    @EncryptedField
    private String otp;
    private LocalDateTime expirationTime;
    private int otpAttempts;
    private LocalDateTime firstAttemptTime;
    private LocalDateTime lockoutTime;
}
