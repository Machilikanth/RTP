package com.toucan.tourtptrs.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.toucan.tourtptrs.constants.EnumConstants.EntityTypeEnum;

@Service
public class AbstractCoreService<T> {

	@Autowired
    protected MongoTemplate mongoTemplate;
    
	 @SuppressWarnings("unchecked")
		public <T> T convertObjectPerEnum(Object obj, Class clazz) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			System.out.println("In cpc methods" +objectMapper);
			return (T) objectMapper.convertValue(obj, clazz == null ? this.getEntityClass() : clazz);

		}
    
    @SuppressWarnings("unchecked")
	public <T> T entityToDTO(Object obj) {
		Object dto = this.getEntityDTO();
		BeanUtils.copyProperties(obj, dto);
		return (T) dto;
	}

	@SuppressWarnings("unchecked")
	public <T> T dtoToEntity(Object dto) {
		Object obj = this.getEntityObject();
		BeanUtils.copyProperties(dto, obj);
		return (T) obj;
	}
		
    public Class<?> getEntityClass() {
		return null;
    }
    
    public <T> T getEntityObject() {
		return null;
	}

	public <T> T getEntityDTO() {
		return null;
	}
	
	public EntityTypeEnum getEntityTypeEnum() {
		return null;
	}
	
	public <T> T setAccId(String id) {
		return null;
	}
//	@CachePut(value = "create", key = "'rtp' + #root.target.getEntityClass().getSimpleName()")
	public T create(T entity) {
		return (T) mongoTemplate.insert(entity);
	}
	

	@SuppressWarnings("unchecked")
	@CachePut(value = "getall", key = "'rtp' + #root.target.getEntityClass().getSimpleName()")
	public List<T> getAll(T entity) {
		return (List<T>) mongoTemplate.findAll(entity.getClass());
	}
	
	@SuppressWarnings("unchecked")
	@CachePut(value = "inq", key = "'rtp' + #root.target.getEntityClass().getSimpleName()")
	public T getById(String id){
		return (T) mongoTemplate.findById(id, this.getEntityClass());
	}
	
	@CachePut(value = "upd", key = "'rtp' + #root.target.getEntityClass().getSimpleName()")
	public T update(T updatedEntity) {
		return (T) mongoTemplate.save(updatedEntity);
	}

    
}
