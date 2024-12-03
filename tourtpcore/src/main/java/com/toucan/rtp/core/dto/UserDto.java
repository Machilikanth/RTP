package com.toucan.rtp.core.dto;

import com.toucan.rtp.core.constants.AppConstants.TermsandConditionEnum;
import com.toucan.rtp.core.constants.AppConstants.UserStatusEnum;
import com.toucan.rtp.core.model.SimBinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String mobileNumber;
	private String location;
	private String country;
	private String countryCode;
	private String language;
	private String accId;
	private String appPin;
	private String newAppPin;
	private UserStatusEnum status;
	private SimBinding simBinding;
	private TermsandConditionEnum terms;

}
