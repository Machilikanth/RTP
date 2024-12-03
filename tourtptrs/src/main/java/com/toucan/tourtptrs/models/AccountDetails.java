package com.toucan.tourtptrs.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toucan.tourtptrs.helper.UserBankDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userAccountDetails")
public class AccountDetails implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = 922661665543421856L;

	@Id
	private String id;
	
	private ArrayList<UserBankDetails> userBankDetails;

}