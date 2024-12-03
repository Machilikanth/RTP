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
public class KarzaPassportRequestHelper implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -1979940463201689927L;

	@JsonProperty("consent")
	@JsonInclude(Include.NON_NULL)
	private String consent;

	@JsonProperty("fileNo")
	@JsonInclude(Include.NON_NULL)
	private String fileNo;

	@JsonProperty("dob")
	@JsonInclude(Include.NON_NULL)
	private String dob;

	@JsonProperty("passportNo")
	@JsonInclude(Include.NON_NULL)
	private String passportNo;

	@JsonProperty("doi")
	@JsonInclude(Include.NON_NULL)
	private String doi;

	@JsonProperty("name")
	@JsonInclude(Include.NON_NULL)
	private String name;

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
