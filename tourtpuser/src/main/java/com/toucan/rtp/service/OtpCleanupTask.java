package com.toucan.rtp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toucan.rtp.repository.OtpRepository;

@Component
public class OtpCleanupTask {

    @Autowired
    private OtpRepository otpRepository;

    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void cleanupExpiredOtps() {
        otpRepository.deleteByExpirationTimeBefore(LocalDateTime.now());
    }
}
