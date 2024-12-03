package com.toucan.tourtpkyc.services;

import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toucan.tourtpkyc.constants.KycConstants;
import com.toucan.tourtpkyc.models.KarzaDLHelperVO;
import com.toucan.tourtpkyc.models.KarzaDLResponseResult;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.wallet.Wallet;
import com.toucan.tourtpkyc.repositories.WalletRepo;
import com.toucan.tourtpkyc.utils.AesEncryptDecrypt;
import com.toucan.tourtpkyc.utils.KycUtils;
import com.toucan.tourtpkyc.utils.RepoHelpers;

@Service
public class KarzaDLValidationService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KycUtils utils;

    @Autowired
    KycConstants kycConstants;

    @Autowired
    AesEncryptDecrypt crypto;
    
    @Autowired
    WalletRepo walletRepo;
    
    @Autowired
    RepoHelpers repoHelpers;

    public KarzaResponse<KarzaDLResponseResult> postValidation(KarzaDLHelperVO helper, String userId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        KarzaResponse<KarzaDLResponseResult> result = new KarzaResponse<>();
        try {

            HttpHeaders headers = utils.karzaHeaders();

            Map<String, Object> request = setRequest(helper);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            KarzaResponse<KarzaDLResponseResult> response = restTemplate.postForObject(URI.create(kycConstants.KarzaDL), entity, KarzaResponse.class);

            if (response.getStatusCode() == HttpStatusCode.valueOf(101)) {

                ObjectMapper mapper = new ObjectMapper();

                KarzaDLResponseResult dLResponseResult = mapper.convertValue(response.getResult(), KarzaDLResponseResult.class);
                
                // TODO: replace if else with optional
                if (dLResponseResult.getStatus().equals("Active")) {
                    result.setStatusCode(HttpStatusCode.valueOf(200));
                    result.setMessage("kyc verified");
                    
                    Wallet walletData = repoHelpers.saveWalletStatus(userId);
                    walletRepo.save(walletData);
                    
                } else if (dLResponseResult.getStatus().equals("Unactive")) {
                    result.setStatusCode(HttpStatusCode.valueOf(200));
                    result.setMessage("DL is not active");
                } else {
                    result.setStatusCode(HttpStatusCode.valueOf(200));
                    result.setMessage("Something went wrong");
                }
            } else {
                result.setStatusCode(response.getStatusCode());
                result.setMessage("Request Failed");
            }

        } catch (RuntimeException e) {
            System.out.println(e);
            result.setMessage(e.getMessage());

        }
        return result;
    }

    private Map<String, Object> setRequest(KarzaDLHelperVO helper) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        Map<String, Object> map = new HashMap<>();
        map.put("consent", "Y");

        map.put("dob", crypto.decrypt(helper.getDateOfBirth()));
        map.put("dlNo", crypto.decrypt(helper.getDlNumber()));

        return map;
    }
}
