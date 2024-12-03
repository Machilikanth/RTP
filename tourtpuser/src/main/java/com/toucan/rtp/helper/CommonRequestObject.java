package com.toucan.rtp.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.toucan.rtp.constants.AppConstants.EntityTypeEnum;
import com.toucan.rtp.constants.AppConstants.SortOrderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommonRequestObject {
	private boolean success;
	private String message;
	private Object requestObject;
	private EntityTypeEnum entityTypeEnum;
	private String pbkId;
	
	public static CommonRequestObject buildRequest(boolean success, String message, Object requestObject , EntityTypeEnum entityTypeEnum,String pbkId) {
		CommonRequestObject commonRequestObject = new CommonRequestObject();
		commonRequestObject.setSuccess(success);
		commonRequestObject.setMessage(message);
		commonRequestObject.setRequestObject(requestObject);
		commonRequestObject.setEntityTypeEnum(entityTypeEnum);
		commonRequestObject.setPbkId(pbkId);
		return commonRequestObject;
	}

}
