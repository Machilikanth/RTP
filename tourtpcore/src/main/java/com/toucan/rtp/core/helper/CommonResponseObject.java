package com.toucan.rtp.core.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.toucan.rtp.core.constants.AppConstants.EntityTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class CommonResponseObject {
	
	private boolean success;
	private String message;
	private Object responseObject;
	private int statusCode;
	private EntityTypeEnum entityTypeEnum;
    private String token;
    private String b_Pk;
    private String b_id;
    private String refreshToken;
    
    
    
    
    
    
	public static CommonResponseObject buildResponse(boolean success, String message, Object responseObject, int statusCode, EntityTypeEnum entityTypeEnum,String token, String refreshToken) {
		CommonResponseObject commonResponseObject = new CommonResponseObject();
		commonResponseObject.setSuccess(success);
		commonResponseObject.setMessage(message);
		commonResponseObject.setResponseObject(responseObject);
		commonResponseObject.setStatusCode(statusCode);
		commonResponseObject.setEntityTypeEnum(entityTypeEnum);
		commonResponseObject.setToken(token);
		commonResponseObject.setRefreshToken(refreshToken);
		return commonResponseObject;
	}
	
	
	
	
}



