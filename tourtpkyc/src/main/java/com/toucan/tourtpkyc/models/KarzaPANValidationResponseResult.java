package com.toucan.tourtpkyc.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KarzaPANValidationResponseResult implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -8014927483980123671L;
	
	@JsonProperty("status")
	@JsonInclude(Include.NON_NULL)
	private String status;

	@JsonProperty("name")
	@JsonInclude(Include.NON_NULL)
	private String name;
	
	
	@JsonProperty("dob")
	@JsonInclude(Include.NON_NULL)
	private String dob;

}
