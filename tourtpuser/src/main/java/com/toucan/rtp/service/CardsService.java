package com.toucan.rtp.service;

import org.springframework.stereotype.Service;

import com.toucan.rtp.constants.AppConstants.EntityTypeEnum;
import com.toucan.rtp.dto.CardsDto;
import com.toucan.rtp.model.Cards;
@Service
public class CardsService extends AbstractService<Cards>{
	
	@Override
	public Class<Cards> getEntityClass() {
		return Cards.class;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new CardsDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new Cards();
	}

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.CARDS;
	}
	
	
	public Cards create (Cards entity) {
		return mongoTemplate.insert(entity);
	}

}
