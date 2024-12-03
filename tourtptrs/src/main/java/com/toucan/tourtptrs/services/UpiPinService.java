package com.toucan.tourtptrs.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.toucan.tourtptrs.constants.EnumConstants.EntityTypeEnum;
import com.toucan.tourtptrs.dto.UpiPinDto;
import com.toucan.tourtptrs.helper.UserBankDetails;
import com.toucan.tourtptrs.models.AccountDetails;
import com.toucan.tourtptrs.models.UpiPin;
import com.toucan.tourtptrs.models.User;
import com.toucan.tourtptrs.repositories.AccountDetailsRepository;
import com.toucan.tourtptrs.repositories.UpiPinRepository;
import com.toucan.tourtptrs.repositories.UserRepository;

@Service
public class UpiPinService extends AbstractCoreService<UpiPin> {

	@Autowired
	private AccountDetailsRepository accountDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UpiPinRepository upiPinRepository;

	@Override
	public Class<UpiPin> getEntityClass() {
		return UpiPin.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new UpiPinDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new UpiPin();
	}

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.UPIPIN;
	}

	@Override
	public UpiPin create(UpiPin entity) {
		Optional<User> user = userRepository.findById(entity.getUser());
		if (user.isPresent()) {
			User userdata = user.get();
			Optional<AccountDetails> accountdetails = accountDetailsRepository.findById(userdata.getAccId());
			if (accountdetails.isPresent()) {
				Query query = new Query(Criteria.where("userBankDetails.accountNumber").is(entity.getAccNum()));
				AccountDetails accountNumber = mongoTemplate.findOne(query, AccountDetails.class);

				Query query1 = new Query(Criteria.where("accNum").is(entity.getAccNum()));
				UpiPin accountNumber1 = mongoTemplate.findOne(query1, UpiPin.class);

				if (accountNumber != null) {
					if (accountNumber1 == null) {
						entity.setAccNum(entity.getAccNum());
						super.create(entity);
					} else {
						UpiPin pinId = upiPinRepository.findByAccNum(entity.getAccNum());
						pinId.setUpiPin(entity.getUpiPin());
						mongoTemplate.save(pinId);
					}
				} else {
					throw new RuntimeException("Account Number doesn't exist");
				}
			}
		}
		return entity;
	}

}
