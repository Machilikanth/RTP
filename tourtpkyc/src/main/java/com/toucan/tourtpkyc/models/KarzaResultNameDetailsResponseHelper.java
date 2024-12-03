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

public class KarzaResultNameDetailsResponseHelper implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -7072850347999185615L;

	@JsonProperty("nameScore")
	@JsonInclude(Include.NON_NULL)
	private String nameScore;

	@JsonProperty("nameMatch")
	@JsonInclude(Include.NON_NULL)
	private boolean nameMatch;

	@JsonProperty("surnameFromPassport")
	@JsonInclude(Include.NON_NULL)
	private String surnameFromPassport;

	@JsonProperty("nameFromPassport")
	@JsonInclude(Include.NON_NULL)
	private String nameFromPassport;

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

