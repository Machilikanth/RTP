package com.toucan.tourtptrs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.toucan.tourtptrs.helper.CommonRequestObject;
import com.toucan.tourtptrs.helper.CommonResponseObject;
import com.toucan.tourtptrs.helper.LinkBankAccount;
import com.toucan.tourtptrs.services.AbstractCoreService;
import com.toucan.tourtptrs.services.AccountDetailsService;
import com.toucan.tourtptrs.validators.ObjectValidator;

@RestController
@RequestMapping("/toucan/trs/lnkbnkaccctrl")
public class LinkBankAccountController {

	@Autowired
	private ObjectValidator objectValidator;
	
	@Autowired
	private AccountDetailsService accountDetailsService;

	@PostMapping("/link")
    public CommonResponseObject LinkAcc(@RequestBody CommonRequestObject commonRequestObject) {
        try {
        	LinkBankAccount entity = this.buildMapper().convertValue(commonRequestObject.getRequestObject(), LinkBankAccount.class);
        	objectValidator.validate(entity);
            entity = accountDetailsService.linkAcc(entity);
            return CommonResponseObject.buildResponse(true,"UserBankDetails Linked successfully",entity,200,commonRequestObject.getEntityTypeEnum());
        } catch (IllegalStateException e) {
            return CommonResponseObject.buildResponse(false,e.getMessage(),e.getMessage(),400,commonRequestObject.getEntityTypeEnum());
        } catch (Exception e) {
            return CommonResponseObject.buildResponse(false,"An error occurred while Linking UserBankDetails",e.getMessage(),500,commonRequestObject.getEntityTypeEnum());
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
}
