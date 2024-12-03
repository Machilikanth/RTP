package com.toucan.tourtpkyc.models;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import lombok.Data;

@Data
public class KarzaDLResponseResult implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 8037903115318124661L;

	@JsonProperty("status")
	@JsonInclude(Include.NON_NULL)
	private String status;

	@JsonProperty("father/husband")
	@JsonInclude(Include.NON_NULL)
	private String guardian;

	@JsonProperty("bloodGroup")
	@JsonInclude(Include.NON_NULL)
	private String bloodGroup;

	@JsonProperty("name")
	@JsonInclude(Include.NON_NULL)
	private String name;

	@JsonProperty("img")
	@JsonInclude(Include.NON_NULL)
	private String img;

	@JsonProperty("dob")
	@JsonInclude(Include.NON_NULL)
	private String dob;

	@JsonProperty("issueDate")
	@JsonInclude(Include.NON_NULL)
	private String issueDate;

	@JsonProperty("statusDetails")
	@JsonInclude(Include.NON_NULL)
	private KarzaDLResponseStatusDetails statusDetails;

	@JsonProperty("validity")
	@JsonInclude(Include.NON_NULL)
	private KarzaDlResponseValidity validity;

	@JsonProperty("address")
	@JsonInclude(Include.NON_NULL)
	private ArrayList<KarzaDLAddress> address;
	

	@JsonProperty("dlNumber")
	@JsonInclude(Include.NON_NULL)
	private String dlNumber;

	@JsonProperty("covDetails")
	@JsonInclude(Include.NON_NULL)
	private ArrayList<KarzaDLResponseCovDetails> covDetails;

	@JsonIgnore
	public String getJson() {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
