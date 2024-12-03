package com.toucan.tourtpkyc.services;

import java.awt.print.Printable;
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
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.KarzaVoterRequestHelper;
import com.toucan.tourtpkyc.models.KarzaVoterResponseResultHelper;
import com.toucan.tourtpkyc.models.wallet.Wallet;
import com.toucan.tourtpkyc.repositories.WalletRepo;
import com.toucan.tourtpkyc.utils.AesEncryptDecrypt;
import com.toucan.tourtpkyc.utils.KycUtils;
import com.toucan.tourtpkyc.utils.RepoHelpers;

@Service("karzaVoterService")
public class KarzaVoterValidationService {

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

	public KarzaResponse<KarzaVoterResponseResultHelper> postValidation(KarzaVoterRequestHelper kycVoterRequest, String userId) {
		KarzaResponse<KarzaVoterResponseResultHelper> result = new KarzaResponse<>();
		try {

			HttpHeaders headers = utils.karzaHeaders();
			Map<String, Object> request = requestToJson(kycVoterRequest);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

			KarzaResponse<KarzaVoterResponseResultHelper> response = restTemplate.postForObject(URI.create(KycConstants.KarzaVoterId), entity, KarzaResponse.class);

			if (response.getStatus_code() == HttpStatusCode.valueOf(101)) {
				ObjectMapper mapper = new ObjectMapper();
				KarzaVoterResponseResultHelper voterResponseResult = mapper.convertValue(response.getResult(), KarzaVoterResponseResultHelper.class);

				if (voterResponseResult.getName().toLowerCase().equals(crypto.decrypt(kycVoterRequest.getName()).toLowerCase())) {
					result.setStatusCode(HttpStatusCode.valueOf(200));
					result.setMessage("kyc verified");
					Wallet walletData = repoHelpers.saveWalletStatus(userId);
					walletRepo.save(walletData);
				} else {
					result.setStatusCode(HttpStatusCode.valueOf(200));
					result.setMessage("Name and voter id mismatch");
				}

			} else {
				result.setStatusCode(response.getStatusCode());
				result.setMessage("Request Failed");
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(e.getMessage());
			return result;
		}

	}

	private Map<String, Object> requestToJson(KarzaVoterRequestHelper helper) {

		Map<String, Object> map = new HashMap<>();
		map.put("consent", "Y");
		map.put("epic_no", crypto.decrypt(helper.getEpicNumber()));

		return map;
	}
}
