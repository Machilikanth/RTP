package com.toucan.tourtptrs.dto;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toucan.tourtptrs.helper.UserBankDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsDto implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = -2386504605136251478L;

	@Id
	private String id;
	
	private ArrayList<UserBankDetails> UserBankDetails;

}
