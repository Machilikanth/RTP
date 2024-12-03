package com.toucan.rtp.core.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import com.toucan.rtp.core.Validators.ObjectValidator;
import com.toucan.rtp.core.customexception.CustomExceptions.InvalidUserException;
import com.toucan.rtp.core.customexception.CustomExceptions.UserAlreadyExistsException;
import com.toucan.rtp.core.helper.CommonRequestObject;
import com.toucan.rtp.core.helper.CommonResponseObject;
import com.toucan.rtp.core.helper.RefreshTokenRequest;
import com.toucan.rtp.core.model.RefreshToken;
import com.toucan.rtp.core.model.User;
import com.toucan.rtp.core.service.AbstractService;
import com.toucan.rtp.core.service.JwtService;
import com.toucan.rtp.core.service.KeyService;
import com.toucan.rtp.core.service.RefreshTokenService;
import com.toucan.rtp.core.service.UserService;

import jakarta.validation.ValidationException;

@RestController
public abstract class AbstractCoreController<T> {
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private UserService myUserDetailService;

	@Autowired
	private ObjectValidator objectValidator;

	@Autowired
	private AbstractService<T> abstractService;
	
	@Autowired
	private KeyService keyService;
	
	
	@PostMapping("/generate-keys")
	public CommonResponseObject generateKeys() {
		try {
			CommonResponseObject keyPairResponse = keyService.generateAndStoreKeyPair();

			Map<String, Object> responseData = new HashMap<>();
			responseData.put("publicKey", keyPairResponse.getB_Pk());
			responseData.put("id", keyPairResponse.getB_id());

			return CommonResponseObject.buildResponse(true, "Keys generated successfully", responseData, 200,null,null, null);
		} catch (NoSuchAlgorithmException e) {
			return CommonResponseObject.buildResponse(false, "Error generating keys", e.getMessage(), 500,null,null, null);
		}
	}
	@PostMapping("/refreshToken")
	public CommonResponseObject refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		System.out.println("entered");
		try {
			  System.out.println("Inside try block");
		    return refreshTokenService.findByToken(refreshTokenRequest.getToken())
		        .map(token -> {
		            System.out.println("Token found in DB: " + token.getToken());
		            return token;
		        })
		        .map(refreshTokenService::verifyExpiration)
		        .map(RefreshToken::getUser)
		        .map(userInfo -> {
		        	System.out.println("THE USER INFO WE GOT IS "+userInfo);
		            String accessToken = jwtService.generateToken(userInfo);
		            Map<String, Object> responseData = new HashMap<>();
		            responseData.put("accessToken", accessToken);
		            responseData.put("refreshToken", refreshTokenRequest.getToken());

		            return CommonResponseObject.buildResponse(true, "Token refreshed successfully", responseData, 200, null, null, null);
		        })
		        .orElseGet(() -> CommonResponseObject.buildResponse(false, "Refresh token is not in the database!", null, 404, null, null, null));
		} catch (Exception e) {
		    return CommonResponseObject.buildResponse(false, "Error refreshing token", e.getMessage(), 500, null, null, null);
		}
	}

	@PostMapping("/decrypt")
	public CommonResponseObject decryptData(@RequestBody CommonRequestObject commonRequestObject) {
	    try {
	    	System.out.println("the common Request is this "+commonRequestObject);
	        T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
	        entity = abstractService.dtoToEntity(entity);
	        entity = keyService.decryptData(entity, commonRequestObject.getPbkId());
	        return CommonResponseObject.buildResponse(true, "Data decrypted successfully", entity, 200, null, null, null);
	    } catch (Exception e) {
	        return CommonResponseObject.buildResponse(false, "Error decrypting data", e.getMessage(), 500, null, null, null);
	    }
	}

	
	@PostMapping("/register")
	public CommonResponseObject create(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			objectValidator.validate(entity);
			T decrypteddetails= keyService.decryptData(entity, commonRequestObject.getPbkId());
			
			T createdEntity = abstractService.create(decrypteddetails);
			createdEntity = abstractService.entityToDTO(createdEntity);
			User user = (User) entity;
			String token = jwtService.generateToken(myUserDetailService.loadUserByMobileNumber(((User) entity).getMobileNumber()));
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
			T encryptdetails= keyService.encryptData(decrypteddetails,commonRequestObject.getF_Pk());	
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Created Successfully", encryptdetails, HttpStatus.CREATED.value(),
							commonRequestObject.getEntityTypeEnum(), token, refreshToken.getToken())
					: CommonResponseObject.buildResponse(false, "Creation Failed", null, 400,
							commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (UserAlreadyExistsException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		}
	}

	@PostMapping("/login")
	public CommonResponseObject logIn(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			T decrypteddetails= keyService.decryptData(entity, commonRequestObject.getPbkId());
			T loggedInEntity = abstractService.logIn(decrypteddetails);
			objectValidator.validate(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);
			User user = (User) entity;
			String token = jwtService.generateToken(myUserDetailService.loadUserByMobileNumber(((User) entity).getMobileNumber()));
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
			return loggedInEntity != null
					? CommonResponseObject.buildResponse(true, "Otp is Sent Sucessfully", null, HttpStatus.OK.value(),
							commonRequestObject.getEntityTypeEnum(), token, refreshToken.getToken())
					: CommonResponseObject.buildResponse(false, "Login Failed", null, 400,
							commonRequestObject.getEntityTypeEnum(), null, null); 
		} catch (InvalidUserException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		}
	}
	
	@PostMapping("/setapppin")
	public CommonResponseObject pinSetUp(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			T decryteddetais=keyService.decryptData(entity, commonRequestObject.getPbkId());
			T loggedInEntity = abstractService.pinSetUp(decryteddetais);
			objectValidator.validate(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);
			return loggedInEntity != null
					? CommonResponseObject.buildResponse(true, "Pin SetUp is Done", loggedInEntity, 200,
							commonRequestObject.getEntityTypeEnum(), null, null)
					: CommonResponseObject.buildResponse(false, "Pin SetUp Failed", null, 400,
							commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (UserAlreadyExistsException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		}
	}
	
	@PostMapping("/changeapppin")
	public CommonResponseObject validateOtp(@RequestBody CommonRequestObject commonRequestObject) {

		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			T decryteddetails=keyService.decryptData(entity, commonRequestObject.getPbkId());
			T loggedInEntity = abstractService.changePinWithOtp(decryteddetails);
			objectValidator.validate(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);
			return loggedInEntity != null
					? CommonResponseObject.buildResponse(true, "Pin SetUp is Done", loggedInEntity, 200,
							commonRequestObject.getEntityTypeEnum(), null, null)
					: CommonResponseObject.buildResponse(false, "Pin SetUp Failed", null, 400,
							commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (UserAlreadyExistsException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		}
	}
	

	@PostMapping("/inq")
	public CommonResponseObject getById(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			LinkedHashMap<String, String> reqObject = (LinkedHashMap) commonRequestObject.getRequestObject();
			String id = reqObject.get("id");
			if (id == null || id.isEmpty()) {
				return CommonResponseObject.buildResponse(false, "Id Not Found", null, 200,
						commonRequestObject.getEntityTypeEnum(), null, null);
			}
			T createdEntity = abstractService.getById(id);
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Fetched Successfully", createdEntity, 200,
							commonRequestObject.getEntityTypeEnum(), null, null)
					: CommonResponseObject.buildResponse(false, "Record Not Found", null, 200,
							commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 200,
					commonRequestObject.getEntityTypeEnum(), null, null);
		}
	}
	
	@PostMapping("/upd")
	public CommonResponseObject update(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			LinkedHashMap<String, String> requestObject = (LinkedHashMap<String, String>) commonRequestObject
					.getRequestObject();
			String id = requestObject.get("id");
			T existingEntity = (id != null) ? abstractService.getById(id) : null;
			T updatedEntity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			updatedEntity = abstractService.dtoToEntity(updatedEntity);
			updatedEntity = abstractService.update(updatedEntity);
			updatedEntity = abstractService.entityToDTO(updatedEntity);
			return (id == null || id.isEmpty())
					? CommonResponseObject.buildResponse(false, "Id Not Found", null, 400,
							commonRequestObject.getEntityTypeEnum(), null, null)
					: (existingEntity == null)
							? CommonResponseObject.buildResponse(false, "Entity is Null", null, 400,
									commonRequestObject.getEntityTypeEnum(), null, null)
							: (updatedEntity != null)
									? CommonResponseObject.buildResponse(true, "Sucessfully Updated", updatedEntity,
											200, commonRequestObject.getEntityTypeEnum(), null, null)
									: CommonResponseObject.buildResponse(false, "UnExpected Error", null, 400,
											commonRequestObject.getEntityTypeEnum(), null, null);
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 400,
					commonRequestObject.getEntityTypeEnum(), null, null);
		}
	}

}
