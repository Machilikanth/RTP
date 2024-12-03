package com.toucan.tourtpkyc.models;

import org.springframework.http.HttpStatusCode;

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
public class KarzaResponse<T> {
	@JsonIgnore
	private static final long serialVersionUID = -7019330105768438012L;

	// for Pan and DL
	@JsonProperty("requestId")
	@JsonInclude(Include.NON_NULL)
	private String requestId;
	
	// for Voter
	@JsonProperty("request_id")
	@JsonInclude(Include.NON_NULL)
	private String request_id;
	
	// for Pan and DL
	@JsonProperty("statusCode")
	@JsonInclude(Include.NON_NULL)
	private HttpStatusCode statusCode;
	
	// for Voter
	@JsonProperty("status-code")
	@JsonInclude(Include.NON_NULL)
	private HttpStatusCode status_code;
	

	
	@JsonProperty("message")
	@JsonInclude(Include.NON_NULL)
	private String message;


	@JsonProperty("result")
	@JsonInclude(Include.NON_NULL)
	private T result;
	
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
