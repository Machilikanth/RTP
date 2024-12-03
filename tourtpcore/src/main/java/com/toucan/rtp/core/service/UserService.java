package com.toucan.rtp.core.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.toucan.rtp.core.Validators.ObjectValidator;
import com.toucan.rtp.core.constants.AppConstants.EntityTypeEnum;
import com.toucan.rtp.core.constants.AppConstants.TermsandConditionEnum;
import com.toucan.rtp.core.customexception.CustomExceptions.InvalidUserException;
import com.toucan.rtp.core.customexception.CustomExceptions.UserAlreadyExistsException;
import com.toucan.rtp.core.dto.UserDto;
import com.toucan.rtp.core.helper.CommonResponseObject;
import com.toucan.rtp.core.model.Otp;
import com.toucan.rtp.core.model.SimBinding;
import com.toucan.rtp.core.model.User;
import com.toucan.rtp.core.repository.UserRepository;

@Service
public class UserService extends AbstractService<User>/* implements UserDetailsService */ {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KeyService keyService;

	@Autowired
	private ObjectValidator objectValidator;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new UserDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new User();
	}

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.USER;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return null;
//	}

//	public UserDetails loadUserByMobileNumber(String mobileNumber) throws UsernameNotFoundException {
//		Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
//		if (user.isPresent()) {
//			return new CustomUserDetails(user.get());
//		} else {
//			throw new UsernameNotFoundException("User not found with mobile number: " + mobileNumber);
//		}
//	}

	public User loadUserByMobileNumber(String mobileNumber) throws Exception {
		Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new Exception("User not found with mobile number: " + mobileNumber);
		}
	}

	@Override
	public User create(User entity) throws UserAlreadyExistsException {
		try {
			if (entity.getTerms().equals(TermsandConditionEnum.NO)) {
				throw new RuntimeException("User must accept Terms and Conditions.");
			}
			Optional<User> existingUser = userRepository.findByMobileNumber(entity.getMobileNumber());
			 if (existingUser.isEmpty()) {
		            String smsToken = generateSmsToken(entity);
		        } 
//			if (existingUser.isPresent()) {
//				User user = existingUser.get();
//				entity.setId(user.getId());
//				updateSimBinding(entity);
//			} 
//			else {
//				updateSimBinding(entity);
//				userRepository.save(entity);
//			}
			Otp otp = new Otp();
			otp.setPhoneNumber(entity.getMobileNumber());
			otpService.generateOtp(otp);
		} catch (DataIntegrityViolationException e) {
			logger.error("Error creating user due to data integrity violation", e);
			throw new RuntimeException("User creation failed due to data integrity violation.", e);
		} catch (IllegalArgumentException e) {
			logger.error("Invalid argument provided", e);
			throw new IllegalArgumentException("Invalid argument: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Unexpected error occurred", e);
			throw new RuntimeException("An unexpected error occurred: " + e.getMessage(), e);
		}
		return this.update(entity);
	}
	
	public String generateSmsToken(User user) {
    try {
        // Create a string using device information
		String tokenData = user.getSimBinding().getDeviceId()
				+ ":" /* + user.getMobileNumber() + ":" */+ 
                            user.getSimBinding().getOSType() + ":" + user.getSimBinding().getServiceId();
        System.out.println("The Token Data"+ tokenData);
		// Set expiration to 5 minutes
        long expiryTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + 300;
        tokenData += ":" + expiryTimestamp;
         //Encrypt the token data
        return AESKeyGenerator.encrypt(tokenData, "1111111111111111");
    } catch (Exception e) {
        throw new RuntimeException("Failed to generate SMS Token", e);
    }
}
	private void updateSimBinding(User entity) {
		SimBinding simBinding = new SimBinding();
		simBinding.setIMSI(entity.getSimBinding().getIMSI());
		simBinding.setMSISDN(entity.getMobileNumber());
		simBinding.setIMEI(entity.getSimBinding().getIMEI());
		simBinding.setICCID(entity.getSimBinding().getICCID());
		entity.setSimBinding(simBinding);
	}

	@Override
	public User logIn(User entity) throws InvalidUserException {
		Optional<User> userDetails = userRepository.findByMobileNumber(entity.getMobileNumber());
		System.out.println("asdfghjkl"+entity.getMobileNumber());
		
		if (userDetails.isPresent()) {
			User user = userDetails.get();
			System.out.println("user.getSimBinding()"+user.getSimBinding());
			System.out.println("entity.getSimBinding()"+entity.getSimBinding());
			if (user.getSimBinding().equals(entity.getSimBinding())) {
				
				
				Otp otp = new Otp();
				otp.setPhoneNumber(entity.getMobileNumber());
				otpService.generateOtp(otp);

				return user;
			} else {
				throw new InvalidUserException("Invalid SIM binding. Please check the SIM details.");
			}
		} else {
			throw new InvalidUserException("Invalid user. Please check the mobile number.");
		}
	}

	public User pinSetUp(User entity) {
		logger.info("Setting up the user pin For mobile number: {}", entity.getMobileNumber());
		Optional<User> detail = userRepository.findByMobileNumber(entity.getMobileNumber());
		detail.get().setAppPin(entity.getAppPin());
		return mongoTemplate.save(detail.get());
	}

	public User changePinWithOtp(User entity) {
		Optional<User> user = userRepository.findByMobileNumber(entity.getMobileNumber());

		if (user != null) {
			Otp otpEntity = new Otp();
			otpEntity.setPhoneNumber(user.get().getMobileNumber());
			Otp otpobj = otpService.generateOtp(otpEntity);
			String otp = otpobj.getOtp();

			if (otpService.validateOtp(user.get().getMobileNumber(), otp)) {
				if (user.get().getAppPin().equals(entity.getAppPin())) {
					user.get().setAppPin(entity.getNewAppPin());
					return userRepository.save(user.get());
				} else {
					throw new IllegalArgumentException("The pin is incorrect.");
				}
			} else {
				throw new IllegalArgumentException("Invalid OTP.");
			}
		} else {
			throw new IllegalArgumentException("User does not exist.");
		}
	}

}
