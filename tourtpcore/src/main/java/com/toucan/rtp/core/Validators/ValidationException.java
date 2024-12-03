package com.toucan.rtp.core.Validators;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException {

	@JsonIgnore
	private static final long serialVersionUID = 76001396658685445L;

	public ValidationException(String errorMessage) {
		super("Please provide proper details for :: " + errorMessage);
	}
}
