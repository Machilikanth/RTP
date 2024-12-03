package com.toucan.rtp.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.toucan.rtp.constants.AppConstants.TermsandConditionEnum;
import com.toucan.rtp.constants.AppConstants.UserStatusEnum;
import com.toucan.rtp.helper.CustomizeVpa;
import com.toucan.rtp.helper.UPIID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

	@Id
	private String id;

	@NotEmpty(message = "firstname is required")
	private String firstName;

	private String lastName;

	@Email
	@NotEmpty(message = "emailId is requried")
	private String emailId;

	@NotEmpty(message = "mobilenumber is required")
	private String mobileNumber;

	@NotEmpty(message = "location is required")
	private String location;

	@NotEmpty(message = "country is required")
	private String country;

	@NotEmpty(message = "countrycode is required")
	private String countryCode;

	private String language;
  
	private String accId;
	
	private String appPin;
	
	private String newAppPin;
	
	private UserStatusEnum status;
	
	private SimBinding simBinding;
	
	private List<UPIID> vpa;
	
	private TermsandConditionEnum terms;
	
	private List<CustomizeVpa> customVpa;

	
}
