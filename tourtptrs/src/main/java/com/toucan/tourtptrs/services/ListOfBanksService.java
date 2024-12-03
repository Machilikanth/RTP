package com.toucan.tourtptrs.services;

import org.springframework.stereotype.Service;

import com.toucan.tourtptrs.constants.EnumConstants.EntityTypeEnum;
import com.toucan.tourtptrs.dto.ListOfBanksDto;
import com.toucan.tourtptrs.models.ListOfBanks;

@Service
public class ListOfBanksService extends AbstractCoreService<ListOfBanks> {

	@Override
	public Class<ListOfBanks> getEntityClass() {
		return ListOfBanks.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new ListOfBanksDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new ListOfBanks();
	}

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.ACCOUNTDETAILS;
	}
}
