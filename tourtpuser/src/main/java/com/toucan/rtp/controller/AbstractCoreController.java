package com.toucan.rtp.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.toucan.rtp.Validators.ObjectValidator;
import com.toucan.rtp.Validators.ValidationException;
import com.toucan.rtp.customexception.CustomExceptions.InvalidUserException;
import com.toucan.rtp.customexception.CustomExceptions.UserAlreadyExistsException;
import com.toucan.rtp.helper.CommonRequestObject;
import com.toucan.rtp.helper.CommonResponseObject;
import com.toucan.rtp.service.AbstractService;

@RestController
public abstract class AbstractCoreController<T> {

	@Autowired
	private ObjectValidator objectValidator;

	@Autowired
	private AbstractService<T> abstractService;

	@PostMapping("/create")
	public CommonResponseObject create(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			objectValidator.validate(entity);
			T createdEntity = abstractService.create(entity);
			createdEntity = abstractService.entityToDTO(createdEntity);

			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Created Successfully", createdEntity, 200,
							commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Creation Failed", null, 400,
							commonRequestObject.getEntityTypeEnum());
		} catch (UserAlreadyExistsException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum());
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum());
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum());
		}
	}

	@PostMapping("/apppin")
	public CommonResponseObject pinSetUp(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			T loggedInEntity = abstractService.pinSetUp(entity);
			objectValidator.validate(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);

			return loggedInEntity != null
					? CommonResponseObject.buildResponse(true, "Pin SetUp is Done", loggedInEntity, 200,
							commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Pin SetUp Failed", null, 400,
							commonRequestObject.getEntityTypeEnum());
		} catch (UserAlreadyExistsException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum());
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum());
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum());
		}
	}

	@PostMapping("/changepin")
	public CommonResponseObject validateOtp(@RequestBody CommonRequestObject commonRequestObject) {

		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			entity = abstractService.dtoToEntity(entity);
			T loggedInEntity = abstractService.changePinWithOtp(entity);
			objectValidator.validate(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);
			loggedInEntity = abstractService.entityToDTO(loggedInEntity);
			return loggedInEntity != null
					? CommonResponseObject.buildResponse(true, "Pin SetUp is Done", loggedInEntity, 200,
							commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Pin SetUp Failed", null, 400,
							commonRequestObject.getEntityTypeEnum());
		} catch (UserAlreadyExistsException e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 409,
					commonRequestObject.getEntityTypeEnum());
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID",
					dup.getMessage(), 400, commonRequestObject.getEntityTypeEnum());
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), "Validation Error", 400,
					commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), "Exception Error", 400,
					commonRequestObject.getEntityTypeEnum());
		}
	}

	@PostMapping("/getall")
	public CommonResponseObject getAll(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			T entity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			T createdEntity = (T) abstractService.getAll(entity);
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Created Successfully", createdEntity, 200,
							commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Creation Failed", null, 400,
							commonRequestObject.getEntityTypeEnum());
		} catch (DuplicateKeyException dup) {
			return CommonResponseObject.buildResponse(false, "Duplicate ID found. Please try again with valid ID", null,
					400, commonRequestObject.getEntityTypeEnum());
		} catch (ValidationException valid) {
			return CommonResponseObject.buildResponse(false, valid.getMessage(), null, 400,
					commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 400,
					commonRequestObject.getEntityTypeEnum());
		}
	}

	@PostMapping("/inq")
	public CommonResponseObject getById(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			LinkedHashMap<String, String> reqObject = (LinkedHashMap) commonRequestObject.getRequestObject();
			String id = reqObject.get("id");
			if (id == null || id.isEmpty()) {
				return CommonResponseObject.buildResponse(false, "Id Not Found", null, 200,
						commonRequestObject.getEntityTypeEnum());
			}
			T createdEntity = abstractService.getById(id);
			return createdEntity != null
					? CommonResponseObject.buildResponse(true, "Fetched Successfully", createdEntity, 200,
							commonRequestObject.getEntityTypeEnum())
					: CommonResponseObject.buildResponse(false, "Record Not Found", null, 200,
							commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 200,
					commonRequestObject.getEntityTypeEnum());
		}
	}

	@PostMapping("/upd")
	public CommonResponseObject update(@RequestBody CommonRequestObject commonRequestObject) {
		try {
			LinkedHashMap<String, String> requestObject = (LinkedHashMap<String, String>) commonRequestObject
					.getRequestObject();
			String id = requestObject.get("id");
			T existingEntity = (id != null) ? abstractService.getById(id) : null;
			T updatedEntity = abstractService.convertObjectPerEnum(commonRequestObject.getRequestObject(), null);
			updatedEntity = abstractService.dtoToEntity(updatedEntity);
			updatedEntity = abstractService.update(updatedEntity);
			updatedEntity = abstractService.entityToDTO(updatedEntity);
			return (id == null || id.isEmpty())
					? CommonResponseObject.buildResponse(false, "Id Not Found", null, 400,
							commonRequestObject.getEntityTypeEnum())
					: (existingEntity == null)
							? CommonResponseObject.buildResponse(false, "Entity is Null", null, 400,
									commonRequestObject.getEntityTypeEnum())
							: (updatedEntity != null)
									? CommonResponseObject.buildResponse(true, "Sucessfully Updated", updatedEntity,
											200, commonRequestObject.getEntityTypeEnum())
									: CommonResponseObject.buildResponse(false, "UnExpected Error", null, 400,
											commonRequestObject.getEntityTypeEnum());
		} catch (Exception e) {
			return CommonResponseObject.buildResponse(false, e.getMessage(), null, 400,
					commonRequestObject.getEntityTypeEnum());
		}
	}

}
