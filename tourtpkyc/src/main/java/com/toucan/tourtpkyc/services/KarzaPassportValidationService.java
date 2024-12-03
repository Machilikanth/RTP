package com.toucan.tourtpkyc.services;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.toucan.tourtpkyc.constants.KycConstants;
import com.toucan.tourtpkyc.models.KarzaPassportHelperVO;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.KarzaResultPassportNumberResponseHelper;
import com.toucan.tourtpkyc.models.wallet.Wallet;
import com.toucan.tourtpkyc.repositories.WalletRepo;
import com.toucan.tourtpkyc.utils.AesEncryptDecrypt;
import com.toucan.tourtpkyc.utils.KycUtils;
import com.toucan.tourtpkyc.utils.RepoHelpers;

@Service("karzaPassportValidationService")
public class KarzaPassportValidationService {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	KycUtils utils;
	
	@Autowired
	AesEncryptDecrypt crypto;
	
	@Autowired
	RepoHelpers repoHelpers;
	
	@Autowired
	WalletRepo walletRepo;

	public KarzaResponse<KarzaResultPassportNumberResponseHelper> postValidation(KarzaPassportHelperVO kycPassportRequest, String userId) {
		KarzaResponse<KarzaResultPassportNumberResponseHelper> result = new KarzaResponse<>();
		try {

			HttpHeaders headers = utils.karzaHeaders();
			Map<String, Object> request = requestToJson(kycPassportRequest);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
			KarzaResponse<KarzaResultPassportNumberResponseHelper> response = restTemplate.postForObject(URI.create(KycConstants.KarzaPassport), entity, KarzaResponse.class);


			if (response.getStatusCode() == HttpStatusCode.valueOf(101)) {
				result.setStatusCode(HttpStatusCode.valueOf(200));
				result.setMessage("kyc verified");
                Wallet walletData = repoHelpers.saveWalletStatus(userId);
                walletRepo.save(walletData);
			} else {
				result.setStatusCode(response.getStatusCode());
				result.setMessage("Request Failed");
			}

		}catch(RuntimeException e ) {
			System.out.println(e);
			result.setMessage(e.getMessage());

		}
		return result;
	}

//	private String formateDate(LocalDate date) {
//		String day = "" + date.getDayOfMonth();
//		day = day.length() < 2 ? "0" + date.getDayOfMonth()
//		: "" + date.getDayOfMonth();
//		String month = "" + date.getMonthValue();
//		month = month.length() < 2 ? "0" + date.getMonthValue()
//		: "" + date.getMonthValue();
//
//		return day + "/" + month + "/" + date.getYear();
//
//	}

	private Map<String, Object> requestToJson(KarzaPassportHelperVO helper) {
		Map<String, Object> map = new HashMap<>();
		map.put("consent","Y");
		map.put("fileNo",crypto.decrypt(helper.getPassportNumber()));
		map.put("dob", crypto.decrypt(helper.getDateOfBirth()));

		return map;
	}
}

