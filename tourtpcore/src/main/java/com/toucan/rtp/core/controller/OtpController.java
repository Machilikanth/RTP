package com.toucan.rtp.core.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucan.rtp.core.Validators.ObjectValidator;
import com.toucan.rtp.core.configuration.JacksonConfig;
import com.toucan.rtp.core.constants.AppConstants.UserStatusEnum;
import com.toucan.rtp.core.customexception.CustomExceptions.GeneralServiceException;
import com.toucan.rtp.core.customexception.CustomExceptions.OtpServiceException;
import com.toucan.rtp.core.customexception.CustomExceptions.OtpValidationException;
import com.toucan.rtp.core.customexception.CustomExceptions.UserNotFoundException;
import com.toucan.rtp.core.helper.CommonRequestObject;
import com.toucan.rtp.core.helper.CommonResponseObject;
import com.toucan.rtp.core.model.Otp;
import com.toucan.rtp.core.model.User;
import com.toucan.rtp.core.repository.UserRepository;
import com.toucan.rtp.core.service.KeyService;
import com.toucan.rtp.core.service.OtpService;

@RestController
@RequestMapping("/toucan/core/otp")
public class OtpController extends AbstractCoreController<Otp> {
	Logger logger = LoggerFactory.getLogger(OtpController.class);

	@Autowired
	private JacksonConfig jacksonConfig;
	@Autowired
	private ObjectValidator objectValidator;
	@Autowired
	private OtpService otpService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KeyService keyService;

	@PostMapping("/validate")
	public CommonResponseObject validateOtp(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			Otp decryptedOtp = jacksonConfig.buildMapper().convertValue(commonRequestObject.getRequestObject(),
					Otp.class);
			Otp decryteddetails = keyService.decryptData(decryptedOtp, commonRequestObject.getPbkId());
			objectValidator.validate(decryteddetails);
			boolean isValid = otpService.validateOtp(decryteddetails.getPhoneNumber(), decryteddetails.getOtp());
			if (isValid) {
				logger.info("OTP successfully validated for phone number: {}", decryteddetails.getPhoneNumber());
				Optional<User> userOptional = userRepository.findByMobileNumber(decryteddetails.getPhoneNumber());
				if (userOptional.isEmpty()) {
					throw new UserNotFoundException(
							"User not found for phone number: " + decryteddetails.getPhoneNumber());
				}
				User user = userOptional.get();
				user.setStatus(UserStatusEnum.ACTIVE);
				userRepository.save(user);

				return CommonResponseObject.buildResponse(true, "OTP successfully verified", user,
						HttpStatus.ACCEPTED.value(), commonRequestObject.getEntityTypeEnum(), null, null);
			} else {
				throw new OtpValidationException("Incorrect OTP. Please provide a valid OTP");
			}
		} catch (OtpValidationException | UserNotFoundException e) {
			logger.warn("Validation error: {}", e.getMessage());
			return CommonResponseObject.buildResponse(false, e.getMessage(), e.getMessage(),
					HttpStatus.UNAUTHORIZED.value(), commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			logger.error("Exception occurred: {}", e.getMessage(), e);
			throw new GeneralServiceException("Exception Occurred: " + e.getMessage());
		}
	}

	@PostMapping("/resend")
	public CommonResponseObject resendOtp(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			logger.info("Resend OTP request received for entity type: {}", commonRequestObject.getEntityTypeEnum());
			Otp otp = jacksonConfig.buildMapper().convertValue(commonRequestObject.getRequestObject(), Otp.class);
			Otp decrypteddetails = keyService.decryptData(otp, commonRequestObject.getPbkId());
			logger.debug("Decrypted OTP details: {}", decrypteddetails);
			objectValidator.validate(decrypteddetails);
			otp = otpService.resendotp(decrypteddetails);
			logger.info("OTP resent successfully");
			return CommonResponseObject.buildResponse(true, "OTP updated successfully", otp, HttpStatus.OK.value(),
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (OtpValidationException e) {
			logger.error("OTP validation error: {}", e.getMessage());
			return CommonResponseObject.buildResponse(false, null, e.getMessage(), HttpStatus.BAD_REQUEST.value(),
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (OtpServiceException e) {
			logger.error("OTP service error: {}", e.getMessage());
			return CommonResponseObject.buildResponse(false, null, e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value(), commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			logger.error("Exception occurred while resending OTP: {}", e.getMessage());
			throw new GeneralServiceException("Exception occurred while resending OTP: " + e.getMessage());
		}
	}
}
