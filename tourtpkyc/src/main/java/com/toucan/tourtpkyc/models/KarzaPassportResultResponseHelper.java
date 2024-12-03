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
public class KarzaPassportResultResponseHelper implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -2508661252661998556L;

	@JsonProperty("applicationDate")
	@JsonInclude(Include.NON_NULL)
	private String applicationDate;

	@JsonProperty("typeOfApplication")
	@JsonInclude(Include.NON_NULL)
	private String typeOfApplication;

	@JsonProperty("passportNumber")
	@JsonInclude(Include.NON_NULL)
	private KarzaResultPassportNumberResponseHelper passportNumber;

	@JsonProperty("dateofIssue")
	@JsonInclude(Include.NON_NULL)
	private KarzaResultIssueDateResponseHelper dateofIssue;

	@JsonProperty("name")
	@JsonInclude(Include.NON_NULL)
	private KarzaResultNameDetailsResponseHelper name;

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
