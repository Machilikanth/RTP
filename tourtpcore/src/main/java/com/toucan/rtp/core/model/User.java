package com.toucan.rtp.core.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.toucan.rtp.core.constants.AppConstants.TermsandConditionEnum;
import com.toucan.rtp.core.constants.AppConstants.UserStatusEnum;
import com.toucan.rtp.core.helper.UPIID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User implements Serializable {
	private static final long serialVersionUID = -7862933306762692038L;

	@Id
	private String id;

	private String firstName;

	private String lastName;

	private String emailId;

	@EncryptedField
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

	private List<UPIID> vpas;

	private TermsandConditionEnum terms;

}
