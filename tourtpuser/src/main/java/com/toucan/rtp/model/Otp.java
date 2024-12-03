package com.toucan.rtp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "otps")
public class Otp {
    @Id
    private String id; 
    private String phoneNumber;
    private String otp;
    private LocalDateTime expirationTime;
    private int otpAttempts;
    private LocalDateTime firstAttemptTime;
    private LocalDateTime lockoutTime;
}
