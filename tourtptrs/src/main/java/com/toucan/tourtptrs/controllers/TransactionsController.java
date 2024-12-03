//package com.toucan.tourtptrs.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
//import com.toucan.tourtptrs.helper.CommonRequestObject;
//import com.toucan.tourtptrs.helper.CommonResponseObject;
//import com.toucan.tourtptrs.models.Transaction_Entries;
//import com.toucan.tourtptrs.services.AbstractCoreService;
//import com.toucan.tourtptrs.services.TransactionsService;
//import com.toucan.tourtptrs.validators.ObjectValidator;
//
//@RestController
//@RequestMapping("/toucan/trs/transactions")
//public class TransactionsController<T> extends AbstractCoreService<Transaction_Entries>{
//	
//	@Autowired
//	private TransactionsService transactionsService;
//	
//	@Autowired
//	private ObjectValidator objectValidator;
//
//	@PostMapping("/txn")
//	public CommonResponseObject transaction(@RequestBody CommonRequestObject commonRequestObject) {
//		try {
//		Transaction_Entries entity = this.buildMapper().convertValue(commonRequestObject.getRequestObject(), Transaction_Entries.class);
//        objectValidator.validate(entity);
//        entity = transactionsService.transactions(entity);
//        return CommonResponseObject.buildResponse(true, "Transaction Successfully", entity, 0, getEntityTypeEnum());  
//		} catch (IllegalStateException e) {
//          return CommonResponseObject.buildResponse(false,"IllegalStateException occured",e.getMessage(),400,commonRequestObject.getEntityTypeEnum());
//      } catch (Exception e) {
//          return CommonResponseObject.buildResponse(false,"Transaction Failed by Exception",e.getMessage(),500,commonRequestObject.getEntityTypeEnum());
//      }
//	}
//	
//	protected ObjectMapper buildMapper() {
//        ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
//                .registerModule(new Jdk8Module());
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        objectMapper.registerModule(javaTimeModule);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        return objectMapper;
//    }
//	
//}
