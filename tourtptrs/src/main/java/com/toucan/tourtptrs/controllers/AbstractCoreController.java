package com.toucan.tourtptrs.controllers;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.toucan.tourtptrs.customeexception.CustomException.UserAlreadyExistsException;
import com.toucan.tourtptrs.helper.CommonRequestObject;
import com.toucan.tourtptrs.helper.CommonResponseObject;
import com.toucan.tourtptrs.services.AbstractCoreService;
import com.toucan.tourtptrs.validators.ObjectValidator;
import com.toucan.tourtptrs.validators.ValidationException;

@RestController
public class AbstractCoreController<T> {

	@Autowired
	private AbstractCoreService<T> abstractCoreService;
	
	@Autowired
	private ObjectValidator objectValidator;
	
	@PostMapping("/create")
	public CommonResponseObject create(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractCoreService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractCoreService.dtoToEntity(entity);
			objectValidator.validate(entity);
			T createdEntity =  abstractCoreService.create(entity);
			createdEntity = abstractCoreService.entityToDTO(createdEntity);
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Created Successfully", createdEntity, 200, commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Creation Failed", null, 400, commonRequestObject.getEntityTypeEnum());
		}
		catch (UserAlreadyExistsException e) {
	        return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409, commonRequestObject.getEntityTypeEnum());
		}
		catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID", dup.getMessage(),400, commonRequestObject.getEntityTypeEnum());
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(),"Validation Error", 400, commonRequestObject.getEntityTypeEnum());
		} catch(Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error",400, commonRequestObject.getEntityTypeEnum());
		}
	}
	
	@PostMapping("/getall")
	public CommonResponseObject getAll(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = (T) abstractCoreService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			T createdEntity = (T) abstractCoreService.getAll(entity);
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Created Successfully", createdEntity, 200, commonRequestObject.getEntityTypeEnum())
							: CommonResponseObject.buildResponse(false, "Creation Failed", null, 400, commonRequestObject.getEntityTypeEnum());
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID", null,400, commonRequestObject.getEntityTypeEnum());
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), null,400, commonRequestObject.getEntityTypeEnum());
		} catch(Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null,400, commonRequestObject.getEntityTypeEnum());
		}
	}
	
	@PostMapping("/inq")
	public CommonResponseObject getById(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			LinkedHashMap<String, String> reqObject = (LinkedHashMap) commonRequestObject.getRequestObject();
			String id = reqObject.get("id");
			if (id == null || id.isEmpty()) {
				return CommonResponseObject.buildResponse(false, "Id Not Found", null, 200, commonRequestObject.getEntityTypeEnum());
			}
			T createdEntity = (T) abstractCoreService.getById(id);
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Fetched Successfully", createdEntity, 200,
							commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Record Not Found", null, 200, commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 200, commonRequestObject.getEntityTypeEnum());
		}
	}
	
	@PostMapping("/upd")
	public CommonResponseObject update(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			LinkedHashMap<String, String> requestObject = (LinkedHashMap<String, String>) commonRequestObject.getRequestObject();
			String id = requestObject.get("id");
			T existingEntity = (id != null) ? (T) abstractCoreService.getById(id) : null;
			T updatedEntity = (T) abstractCoreService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			updatedEntity = (T) abstractCoreService.dtoToEntity(updatedEntity);
			updatedEntity = (T) abstractCoreService.update(updatedEntity);
			updatedEntity = (T) abstractCoreService.entityToDTO(updatedEntity);
			return (id == null || id.isEmpty())
					? CommonResponseObject.buildResponse(false, "Id Not Found", null, 400, commonRequestObject.getEntityTypeEnum())
					: (existingEntity == null)
							? CommonResponseObject.buildResponse(false, "Entity is Null", null, 400, commonRequestObject.getEntityTypeEnum())
							: (updatedEntity != null)
									? CommonResponseObject.buildResponse(true, "Sucessfully Updated",updatedEntity, 200, commonRequestObject.getEntityTypeEnum())
									: CommonResponseObject.buildResponse(false, "UnExpected Error", null, 400, commonRequestObject.getEntityTypeEnum()); 
		} catch(Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 400, commonRequestObject.getEntityTypeEnum());
		}
	}
}
