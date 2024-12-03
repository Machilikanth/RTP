package com.toucan.tourtptrs.controllers;

import java.util.List;

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
import com.toucan.tourtptrs.helper.UPIID;
import com.toucan.tourtptrs.helper.UserBankDetails;
import com.toucan.tourtptrs.models.AccountDetails;
import com.toucan.tourtptrs.models.User;
import com.toucan.tourtptrs.services.AccountDetailsService;
import com.toucan.tourtptrs.validators.ObjectValidator;
@RestController
@RequestMapping("/toucan/trs/acctdtls")
public class AccountDetailsController extends AbstractCoreController<AccountDetails>{
	@Autowired
	private AccountDetailsService accountDetailsService;
	@Autowired
	private ObjectValidator objectValidator;
	
	
	@PostMapping("/changevpa")
    public CommonResponseObject changevpa(@RequestBody CommonRequestObject commonRequestObject) {

        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
            AccountDetails user = this.buildMapper().convertValue(commonRequestObject.getRequestObject(), AccountDetails.class);
            objectValidator.validate(commonRequestObject);
List<UserBankDetails> bankDetails = user.getUserBankDetails();
	        
	        for (UserBankDetails details : bankDetails) {
	            String mobilenumber = details.getMobileNumber();
	            List<UPIID> upiidList = details.getVpas();
	            
	            if (upiidList.size() < 2) {
	                commonResponseObject.setSuccess(false);
	                commonResponseObject.setMessage("At least 2 VPAs are required.");
	                commonResponseObject.setStatusCode(400);
	                return commonResponseObject;
	            }
 	            String oldVpa = upiidList.get(0).getVpa();
	            String newCustomVpa = upiidList.get(1).getVpa();
 
System.out.println("-------------------------------------------------");
	                System.out.println("old vpa: "+oldVpa);
	                System.out.println("new vpa: " + newCustomVpa);
                if (newCustomVpa == null || newCustomVpa.isEmpty()) {
                    commonResponseObject.setSuccess(false);
                    commonResponseObject.setMessage("Customize VPA is required.");
                    commonResponseObject.setStatusCode(400);
                    return commonResponseObject;
                }

                UserBankDetails uservpa = accountDetailsService.customizeVpa(user.getId(), oldVpa, mobilenumber, newCustomVpa);
                if (uservpa != null) {
                    commonResponseObject.setSuccess(true);
                    commonResponseObject.setMessage("Successfully VPA created");
                    commonResponseObject.setResponseObject(uservpa);
                    commonResponseObject.setStatusCode(200);
                } else {
                    commonResponseObject.setSuccess(false);
                    commonResponseObject.setMessage("Incorrect credentials. Please provide valid credentials.");
                    commonResponseObject.setStatusCode(400);
                    return commonResponseObject;
                }
            }
        } catch (Exception e) {
            return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception occurred while creating the VPA",
                    400, commonRequestObject.getEntityTypeEnum());
        }
        return commonResponseObject;
    }
	@PostMapping("/vpasuggestions")
    public CommonResponseObject getSuggestions(@RequestBody CommonRequestObject commonRequestObject) {
        CommonResponseObject commonResponseObject = new CommonResponseObject();
        try {
        	 User user = this.buildMapper().convertValue(commonRequestObject.getRequestObject(), User.class);
 	        objectValidator.validate(commonRequestObject);
            List<String> suggestions = accountDetailsService.createVPAsuggestions(user);
            commonResponseObject.setSuccess(true);
            commonResponseObject.setMessage("Successfully generated VPA suggestions.");
            commonResponseObject.setResponseObject(suggestions);
            commonResponseObject.setStatusCode(200);
        } catch (Exception e) {
            commonResponseObject = CommonResponseObject.buildResponse(false, e.getMessage(),
                    "Exception occurred while generating VPA suggestions.", 500, null);
        }

        return commonResponseObject;
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
