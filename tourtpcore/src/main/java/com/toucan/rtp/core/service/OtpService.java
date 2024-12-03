package com.toucan.rtp.core.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toucan.rtp.core.constants.AppConstants.EntityTypeEnum;
import com.toucan.rtp.core.dto.OtpDto;
import com.toucan.rtp.core.model.Otp;
import com.toucan.rtp.core.repository.OtpRepository;

@Service
public class OtpService extends AbstractService<Otp> {

	@Autowired
	private OtpRepository otpRepository;
	
	@Override
	public Class<Otp> getEntityClass() {
		return Otp.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public OtpDto getEntityDTO() {
        return new OtpDto();
    }
	
	@SuppressWarnings("unchecked")
	@Override
    public <T> T getEntityObject() {
        return (T)new Otp();
    }

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.USER;
	}
	
	public Otp generateOtp(Otp otp) {
	    Otp otpEntity;
	    String otpCode = String.format("%06d", new Random().nextInt(999999));
	    LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(1); // Set expiration to 1 minute
	    // Check if the phone number already exists
//	    User user = userRepository.findByMobileNumber(otp.getPhoneNumber());
//	    if (user == null || !otp.getPhoneNumber().equals(user.getMobileNumber())) {
//	        throw new RuntimeException("Please provide a registered mobile number");
//	    }
 
	    Optional<Otp> existingOtpOpt = otpRepository.findByPhoneNumber(otp.getPhoneNumber());
	    if (existingOtpOpt.isPresent()) {
	        otpEntity = existingOtpOpt.get();
//	        System.out.println("ATTEMPTS----->" + otpEntity.getOtpAttempts());
 otpEntity.setOtp(otpCode);
	        LocalDateTime lockoutTime = otpEntity.getLockoutTime();
//	        if (lockoutTime != null && LocalDateTime.now().isBefore(lockoutTime)) {
//	            throw new IllegalStateException("Your OTP attempts are completed. You cannot generate more OTPs until the lockout period is over.");
//	        }
// 	        if (lockoutTime != null && LocalDateTime.now().isAfter(lockoutTime)) {
//	            otpEntity.setOtpAttempts(3);
//	            otpEntity.setLockoutTime(null);
//	        }
// 	        if (otpEntity.getOtpAttempts() > 0) {
	            otpEntity.setOtp(otpCode);
	            otpEntity.setExpirationTime(expirationTime);
//	            otpEntity.setOtpAttempts(otpEntity.getOtpAttempts() - 1);
//	            if (otpEntity.getOtpAttempts() == 0) {
//	                otpEntity.setLockoutTime(LocalDateTime.now().plusMinutes(3)); // Lockout for 3 minutes
//	            }
//	            sendOtp(otpEntity, otpCode); // Method to send OTP via SMS
 
//	        } else {
//	            // If attempts are exhausted, set lockout time
//	            otpEntity.setLockoutTime(LocalDateTime.now().plusMinutes(3)); // Lockout for 3 minutes
//	            throw new IllegalStateException("Your OTP attempts are completed. You cannot generate more OTPs until the lockout period is over.");
//	        }
	    } else {
	        // Initialize new OTP record with 3 attempts
	        otpEntity = new Otp(null, otp.getPhoneNumber(), otpCode, expirationTime, 0, null, null);
//	        otpEntity.setOtpAttempts(otpEntity.getOtpAttempts() - 1); // Decrement after first OTP generation
//	        sendOtp(otpEntity, otpCode); // Method to send OTP via SMS
	    }
	    otpRepository.save(otpEntity);
	    return otpEntity;
	}
 
//	private void sendOtp(Otp otpEntity, String otpCode) {
//	    try {
//	        URL sendUrl = new URL("https://rslri.connectbind.com:8443/bulksms/bulksms?" + SmsSender.USERNAME
//	                + "di78-trans" + SmsSender.PASSWORD + "digi789" + SmsSender.TYPE + "0" + SmsSender.DLR + "1"
//	                + SmsSender.DESTINATION + otpEntity.getPhoneNumber() + SmsSender.SOURCE + "DIGIML" + SmsSender.MSG
//	                + "Dear User, Your one-time password " + otpCode
//	                + " and its valid for 15 mins. Do not share to anyone. Digimiles." + SmsSender.ENTITYID
//	                + "1201159100989151460" + SmsSender.TEMPID + "1107162089216820716");
//	        System.out.println(sendUrl);
//	        RestTemplate restTemplate = new RestTemplate();
//	        String responseEntity = restTemplate.postForObject(sendUrl.toString(), null, String.class);
//	        if (responseEntity != null) {
//	            System.out.println("RESPONSE->>>> " + responseEntity);
//	        }
//	    } catch (Exception ex) {
//	        ex.printStackTrace();
//	    }
//	}
	public boolean validateOtp(String phoneNumber, String otp) {
		Optional<Otp> otpValidate = otpRepository.findByPhoneNumberAndOtp(phoneNumber, otp);
		if (otpValidate.isPresent()) {
			System.out.println("Valid mobile number: " + phoneNumber + " OTP: " + otp);
            otpRepository.delete(otpValidate.get()); 
            return true;
		} else {
    		    System.out.println("Invalid credentials " + phoneNumber + " OTP: " + otp);
    		    Optional<Otp> otpToDelete = otpRepository.findByPhoneNumber(phoneNumber);
    		    if (otpToDelete.isPresent()) {
    		        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    		        scheduler.schedule(() -> {
    		            otpRepository.delete(otpToDelete.get());
    		            System.out.println("Invalid OTP, entry in DB deleted after 1 minute");
    		        }, 1, TimeUnit.MINUTES);
    		    }
    		    return false;
    		}	}
	
	public Otp resendotp(Otp otps) {
		Optional<Otp> resend= otpRepository.findByPhoneNumber(otps.getPhoneNumber());
		if (resend.isPresent()) {
	        Otp actualOtp = resend.get();
	        int value=actualOtp.getOtpAttempts()-1;
	        actualOtp.setOtp(generateOtp(otps).getOtp());
            actualOtp.setOtpAttempts(value);
            actualOtp.setExpirationTime(LocalDateTime.now().plusMinutes(1));
	        otpRepository.save(actualOtp);
			return actualOtp;
	    }
		else {
			return null;
		}
	}

}
