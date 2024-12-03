package com.toucan.tourtptrs.models.response;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse<T> {

	@Id
	private String id;
	
	@JsonProperty("statusCode")
	private int statusCode;
	
	@JsonProperty("success")
	private boolean success;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("result")
	private T result;
}
