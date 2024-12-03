package com.toucan.rtp.core.customexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.toucan.rtp.core.customexception.CustomExceptions.GeneralServiceException;
import com.toucan.rtp.core.customexception.CustomExceptions.OtpServiceException;
import com.toucan.rtp.core.customexception.CustomExceptions.OtpValidationException;
import com.toucan.rtp.core.customexception.CustomExceptions.UserNotFoundException;
import com.toucan.rtp.core.helper.CommonResponseObject;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 
		@ExceptionHandler(OtpValidationException.class)
		public ResponseEntity<CommonResponseObject> handleOtpValidationException(OtpValidationException ex) {
			logger.warn("OTP validation exception: {}", ex.getMessage());
			return new ResponseEntity<>(CommonResponseObject.buildResponse(false, ex.getMessage(), ex.getMessage(),
					HttpStatus.UNAUTHORIZED.value(), null, null, null), HttpStatus.UNAUTHORIZED);
		}
	 
		@ExceptionHandler(UserNotFoundException.class)
		public ResponseEntity<CommonResponseObject> handleUserNotFoundException(UserNotFoundException ex) {
			logger.warn("User not found exception: {}", ex.getMessage());
			return new ResponseEntity<>(CommonResponseObject.buildResponse(false, ex.getMessage(), ex.getMessage(),
					HttpStatus.NOT_FOUND.value(), null, null, null), HttpStatus.NOT_FOUND);
		}
	 
		@ExceptionHandler(GeneralServiceException.class)
		public ResponseEntity<CommonResponseObject> handleGeneralServiceException(GeneralServiceException ex) {
			logger.error("General service exception: {}", ex.getMessage());
			return new ResponseEntity<>(CommonResponseObject.buildResponse(false, ex.getMessage(), ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 
		@ExceptionHandler(Exception.class)
		public ResponseEntity<CommonResponseObject> handleException(Exception ex) {
			logger.error("Unhandled exception: {}", ex.getMessage(), ex);
			return new ResponseEntity<>(CommonResponseObject.buildResponse(false, ex.getMessage(),
					"An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 
		@ExceptionHandler(OtpServiceException.class)
		public ResponseEntity<CommonResponseObject> handleOtpServiceException(OtpServiceException ex) {
			return new ResponseEntity<>(CommonResponseObject.buildResponse(false, null, ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	

}
