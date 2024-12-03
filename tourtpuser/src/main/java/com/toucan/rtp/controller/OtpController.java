package com.toucan.rtp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.toucan.rtp.Validators.ObjectValidator;
import com.toucan.rtp.constants.AppConstants.UserStatusEnum;
import com.toucan.rtp.helper.CommonRequestObject;
import com.toucan.rtp.helper.CommonResponseObject;
import com.toucan.rtp.model.Otp;
import com.toucan.rtp.model.User;
import com.toucan.rtp.repository.UserRepository;
import com.toucan.rtp.service.OtpService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/toucan/rtp/otp")
public class OtpController {

	@Autowired
    private RestTemplate restTemplate;
	
    @Autowired
    private OtpService otpService;
    
    @Autowired
	private ObjectValidator objectValidator;
    
    @Autowired
    private UserRepository userRepository;
    
    

//    @PostMapping("/generate")
//    public CommonResponseObject generateOtp(@RequestBody CommonRequestObject commonRequestObject) {
//        try {
//            Otp otp = this.buildMapper().convertValue(commonRequestObject.getRequestObject(), Otp.class);
//            objectValidator.validate(commonRequestObject);
//            otp = otpService.generateOtp(otp);
//            return CommonResponseObject.buildResponse(true,"OTP sent successfully",otp,200,commonRequestObject.getEntityTypeEnum());
//        } catch (IllegalStateException e) {
//            return CommonResponseObject.buildResponse(false,e.getMessage(),null,400,commonRequestObject.getEntityTypeEnum());
//        } catch (Exception e) {
//            return CommonResponseObject.buildResponse(false,"An error occurred while generating OTP",null,500,commonRequestObject.getEntityTypeEnum());
//        }
//    }


    @PostMapping("/validate")
    public CommonResponseObject validateOtp(@RequestBody CommonRequestObject commonRequestObject, HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return CommonResponseObject.buildResponse(false, "Unauthorized: Missing or invalid Authorization header", null, HttpStatus.UNAUTHORIZED.value(), commonRequestObject.getEntityTypeEnum());
            }
            String token = authorizationHeader.substring(7);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token); 
            HttpEntity<CommonRequestObject> requestEntity = new HttpEntity<>(commonRequestObject, headers);
            String decryptUrl = "http://192.168.61.101:30228/toucan/otp/user/decrypt";
            ResponseEntity<CommonResponseObject> responseEntity = restTemplate.exchange(decryptUrl, HttpMethod.POST, requestEntity, CommonResponseObject.class);
            CommonResponseObject decryptResponse = responseEntity.getBody();
            if (decryptResponse == null || !decryptResponse.isSuccess()) {
                return CommonResponseObject.buildResponse(false, "Error decrypting data", null, 500, null);
            }
            Otp decryptedOtp = this.buildMapper().convertValue(decryptResponse.getResponseObject(), Otp.class);
            objectValidator.validate(decryptedOtp);
            boolean isValid = otpService.validateOtp(decryptedOtp.getPhoneNumber(), decryptedOtp.getOtp());
            if (isValid) {
            	System.out.println("the decrypted mobile number is "+decryptedOtp.getPhoneNumber());
                User user = userRepository.findByMobileNumber(decryptedOtp.getPhoneNumber());
                user.setStatus(UserStatusEnum.ACTIVE);
                userRepository.save(user);
                return CommonResponseObject.buildResponse(true, "OTP successfully verified", user, HttpStatus.ACCEPTED.value(), commonRequestObject.getEntityTypeEnum());
            } else {
                return CommonResponseObject.buildResponse(false, "Incorrect OTP. Please provide valid OTP", null, HttpStatus.UNAUTHORIZED.value(), commonRequestObject.getEntityTypeEnum());
            }
        } catch (Exception e) {
            return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), commonRequestObject.getEntityTypeEnum());
        }
    }

 
    
    protected ObjectMapper buildMapper() {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module());
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	
	}
    
    @PostMapping("/resend")
    public CommonResponseObject ResendOtp(@RequestBody CommonRequestObject commonRequestObject) {
    	try {
    		Otp otp = this.buildMapper().convertValue(commonRequestObject.getRequestObject(), Otp.class);
            objectValidator.validate(commonRequestObject);
            otp = otpService.resendotp(otp);
            return CommonResponseObject.buildResponse(true,"OTP updated successfully",otp,200,commonRequestObject.getEntityTypeEnum());
 
    	}catch (Exception e) {
    		return CommonResponseObject.buildResponse(false, null, e.getMessage(), 400, null);
		}
    }

}
