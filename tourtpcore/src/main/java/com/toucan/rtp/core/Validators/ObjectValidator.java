package com.toucan.rtp.core.Validators;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;
    
    public Object validate(Object object) {
    var errors = validator.validate(object);
    if (errors.isEmpty() ) {
        return object;
    } else {
        String errorDetails = errors.stream().map(er -> er.getMessage()).collect(Collectors.joining(", "));
        System.out.println(""+errorDetails);
		
        throw new ValidationException(errorDetails);
     
    }
	}
}
