package com.toucan.tourtpkyc.models;

import java.io.Serializable;

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
public class KarzaDLAddress implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -5183829972489160654L;

	@JsonProperty("district")
	@JsonInclude(Include.NON_NULL)
	private String district;

	@JsonProperty("pin")
	@JsonInclude(Include.NON_NULL)
	private String pin;

	@JsonProperty("completeAddress")
	@JsonInclude(Include.NON_NULL)
	private String completeAddress;

	@JsonProperty("country")
	@JsonInclude(Include.NON_NULL)
	private String country;

	@JsonProperty("state")
	@JsonInclude(Include.NON_NULL)
	private String state;

	@JsonProperty("addressLine1")
	@JsonInclude(Include.NON_NULL)
	private String addressLine1;

	@JsonProperty("type")
	@JsonInclude(Include.NON_NULL)
	private String type;

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
