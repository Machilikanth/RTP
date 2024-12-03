package com.toucan.tourtpkyc.models;


import com.toucan.tourtpkyc.constants.EnumConstants.KycVerificationEnumTypes;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycRequest {
	@NotNull(message = "Please provide idType")
	private KycVerificationEnumTypes idType;
	@NotNull(message = "Please provider userId")
	private String userId;
	private String dlNumber;
	private String dob;
	private String passportNumber;
	private String epicNumber;
	private String name;
	private String panNumber;
//	private String dateOfBirth;
	
}
