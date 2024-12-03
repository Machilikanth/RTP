package com.toucan.tourtptrs.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.toucan.tourtptrs.helper.SimBinding;
import com.toucan.tourtptrs.helper.UPIID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

	
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
	
	private SimBinding simBinding;
	
	private ArrayList<UPIID> vpas;
	
}
