package com.toucan.rtp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toucan.rtp.Validators.ObjectValidator;
import com.toucan.rtp.constants.AppConstants.EntityTypeEnum;
import com.toucan.rtp.constants.AppConstants.TermsandConditionEnum;
import com.toucan.rtp.customexception.CustomExceptions.UserAlreadyExistsException;
import com.toucan.rtp.dto.UserDto;
import com.toucan.rtp.model.Otp;
import com.toucan.rtp.model.SimBinding;
import com.toucan.rtp.model.User;
import com.toucan.rtp.repository.UserRepository;

@Service
public class UserService extends AbstractService<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private OtpService otpService;

	@Autowired
	private ObjectValidator objectValidator;

	@Autowired
	private UserRepository userRepository;

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
	

	@SuppressWarnings("unchecked")
	@Override
	public User create(User entity) throws UserAlreadyExistsException {
	User userDetails = userRepository.findByMobileNumber(entity.getMobileNumber());
	    if (entity.getTerms().equals(TermsandConditionEnum.NO)) {
	        throw new RuntimeException("Accept Terms and Conditions");
	    }
	    if (userDetails != null) {
	        entity.setId(userDetails.getId());
	        SimBinding simBinding = new SimBinding();
	        simBinding.setIMSI(entity.getSimBinding().getIMSI());
	        simBinding.setMSISDN(entity.getMobileNumber());
	        simBinding.setIMEI(entity.getSimBinding().getIMEI());
	        simBinding.setICCID(entity.getSimBinding().getICCID());
	        entity.setSimBinding(simBinding);
	        return this.update(entity);
	    } else {
	        SimBinding simBinding = new SimBinding();
	        simBinding.setIMSI(entity.getSimBinding().getIMSI());
	        simBinding.setMSISDN(entity.getMobileNumber());
	        simBinding.setIMEI(entity.getSimBinding().getIMEI());
	        simBinding.setICCID(entity.getSimBinding().getICCID());
	        entity.setSimBinding(simBinding);
	        mongoTemplate.insert(entity);
	    }
	    Otp otp = new Otp();
	    otp.setPhoneNumber(entity.getMobileNumber());
	    otpService.generateOtp(otp);
	    return entity;
	}

	

//	@SuppressWarnings("unchecked")
//	@Override
//	public User logIn(User entity) throws InvalidUserException {
//		User userDetails = userRepository.findByMobileNumber(entity.getMobileNumber());
//		if (userDetails != null) {
//			if (userDetails.getSimBinding().equals(entity.getSimBinding())) {
//				Otp otp = new Otp();
//				otp.setPhoneNumber(entity.getMobileNumber());
//				otpService.generateOtp(otp);
//
//				return userDetails;
//			} else {
//				throw new InvalidUserException("Invalid SIM binding. Please check the SIM details.");
//			}
//		} else {
//			throw new InvalidUserException("Invalid user. Please check the mobile number.");
//		}
//	}

	public User pinSetUp(User entity) {
		logger.info("Setting up the user pin For mobile number: {}", entity.getMobileNumber());
		User detail = userRepository.findByMobileNumber(entity.getMobileNumber());
		detail.setAppPin(entity.getAppPin());
		objectValidator.validate(detail);
		return mongoTemplate.save(detail);
	}

	public User changePinWithOtp(User entity) {
		// Find the user by phone number
		User user = userRepository.findByMobileNumber(entity.getMobileNumber());

		if (user != null) {
			// Generate OTP
			Otp otpEntity = new Otp();
			otpEntity.setPhoneNumber(user.getMobileNumber());
			Otp otpobj = otpService.generateOtp(otpEntity);
			String otp = otpobj.getOtp();

			// Validate the provided OTP
			if (otpService.validateOtp(user.getMobileNumber(), otp)) {
				// Check if the provided current PIN matches the user's current PIN
				if (user.getAppPin().equals(entity.getAppPin())) {
					// Check if the new PINs match
					user.setAppPin(entity.getNewAppPin());
					return userRepository.save(user);
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
