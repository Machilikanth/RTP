package com.toucan.rtp.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.toucan.rtp.constants.AppConstants.EntityTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommonResponseObject {
	
	private boolean success;
	private String message;
	private Object responseObject;
	private int statusCode;
	private EntityTypeEnum entityTypeEnum;
	
	public static CommonResponseObject buildResponse(boolean success, String message, Object responseObject, int statusCode, EntityTypeEnum entityTypeEnum) {
		CommonResponseObject commonResponseObject = new CommonResponseObject();
		commonResponseObject.setSuccess(success);
		commonResponseObject.setMessage(message);
		commonResponseObject.setResponseObject(responseObject);
		commonResponseObject.setStatusCode(statusCode);
		commonResponseObject.setEntityTypeEnum(entityTypeEnum);
		return commonResponseObject;
	}
}
