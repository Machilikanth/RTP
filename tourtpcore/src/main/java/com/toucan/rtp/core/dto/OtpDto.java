package com.toucan.rtp.core.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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



