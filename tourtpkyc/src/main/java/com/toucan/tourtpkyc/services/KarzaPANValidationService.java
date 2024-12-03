package com.toucan.tourtpkyc.services;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toucan.tourtpkyc.constants.KycConstants;
import com.toucan.tourtpkyc.models.KarzaPANHelperVO;
import com.toucan.tourtpkyc.models.KarzaPANValidationResponseResult;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.wallet.Wallet;
import com.toucan.tourtpkyc.repositories.WalletRepo;
import com.toucan.tourtpkyc.utils.AesEncryptDecrypt;
import com.toucan.tourtpkyc.utils.KycUtils;
import com.toucan.tourtpkyc.utils.RepoHelpers;

@Service("karzaPANValidationService")
public class KarzaPANValidationService {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	KycUtils utils;

	@Autowired
	KycConstants kycConstants;

	@Autowired
	AesEncryptDecrypt crypto;

	@Autowired
	RepoHelpers repoHelpers;

	@Autowired
	WalletRepo walletRepo;

	public KarzaResponse<KarzaPANValidationResponseResult> postValidation(KarzaPANHelperVO kycPanRequest, String userId) {
		KarzaResponse<KarzaPANValidationResponseResult> result = new KarzaResponse<>();
		try {

			HttpHeaders headers = utils.karzaHeaders();
			Map<String, Object> request = requestToJson(kycPanRequest);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

			KarzaResponse<KarzaPANValidationResponseResult> response = restTemplate.postForObject(URI.create(kycConstants.KarzaPAN), entity, KarzaResponse.class);


			if (response.getStatusCode() == HttpStatusCode.valueOf(101)) {

				ObjectMapper mapper = new ObjectMapper();

				KarzaPANValidationResponseResult panResponseResult = mapper.convertValue(response.getResult(), KarzaPANValidationResponseResult.class);

				if(panResponseResult.getName().toLowerCase().equals(crypto.decrypt(kycPanRequest.getName()).toLowerCase()) && panResponseResult.getStatus().equals("Active")) {
					result.setStatusCode(HttpStatusCode.valueOf(200));
					result.setMessage("kyc verified");

					Wallet walletData = repoHelpers.saveWalletStatus(userId);
					walletRepo.save(walletData);
				}else {
					result.setStatusCode(HttpStatusCode.valueOf(200));
					result.setMessage("Name and Pan mismatch");
				}
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

	private Map<String, Object> requestToJson(KarzaPANHelperVO helper) {

		Map<String, Object> map = new HashMap<>();
		map.put("consent","Y");
		map.put("pan",crypto.decrypt(helper.getPanNumber()));

		return map;
	}
}