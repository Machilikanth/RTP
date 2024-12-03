package com.toucan.tourtptrs.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.toucan.tourtptrs.constants.EnumConstants.EntityTypeEnum;

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
	
	public static CommonRequestObject buildRequest(boolean success, String message, Object requestObject , EntityTypeEnum entityTypeEnum) {
		CommonRequestObject commonRequestObject = new CommonRequestObject();
		commonRequestObject.setSuccess(success);
		commonRequestObject.setMessage(message);
		commonRequestObject.setRequestObject(requestObject);
		commonRequestObject.setEntityTypeEnum(entityTypeEnum);
		return commonRequestObject;
	}
}
