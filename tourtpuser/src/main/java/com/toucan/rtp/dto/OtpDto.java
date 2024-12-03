package com.toucan.rtp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {
    @Id
    private String id; 
    private String phoneNumber;
    private String otp;
    private LocalDateTime expirationTime;
//    private int otpAttempts;
    private LocalDateTime firstAttemptTime;
    private LocalDateTime lockoutTime;
}
