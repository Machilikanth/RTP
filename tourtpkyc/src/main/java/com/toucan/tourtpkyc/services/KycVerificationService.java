package com.toucan.tourtpkyc.services;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toucan.tourtpkyc.models.KarzaDLHelperVO;
import com.toucan.tourtpkyc.models.KarzaDLResponseResult;
import com.toucan.tourtpkyc.models.KarzaPANHelperVO;
import com.toucan.tourtpkyc.models.KarzaPassportHelperVO;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.KarzaResultPassportNumberResponseHelper;
import com.toucan.tourtpkyc.models.KarzaVoterRequestHelper;
import com.toucan.tourtpkyc.models.KycRequest;

@Service
public class KycVerificationService {

    @Autowired
    KarzaDLValidationService karzaDLService;

    @Autowired
    KarzaPassportValidationService karzaPassportService;

    @Autowired
    KarzaVoterValidationService karzaVoterService;

    @Autowired
    KarzaPANValidationService karzaPanService;

    public KarzaResponse postKycVerification(KycRequest reqObj) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        KarzaResponse response = new KarzaResponse();

        switch (reqObj.getIdType()) {
            case DL:
                if (reqObj.getDlNumber() != null && reqObj.getDob() != null) {
                    KarzaDLHelperVO karzaDLRequest = createDLRequest(reqObj);

                    response = karzaDLService.postValidation(karzaDLRequest, reqObj.getUserId());
                    return response;
                } else {
                    response = new KarzaResponse<KarzaDLResponseResult>();

                    response.setMessage("date of birth or dl number is not present");;
                    return response;
                }
            case PASSPORT:
                if (reqObj.getPassportNumber() != null && reqObj.getDob() != null) {

                    KarzaPassportHelperVO karzaPassportRequest = createPassportRequest(reqObj);

                    response = karzaPassportService.postValidation(karzaPassportRequest, reqObj.getUserId());
                    return response;
                } else {
                    response = new KarzaResponse<KarzaResultPassportNumberResponseHelper>();

                    response.setMessage("date of birth or passport number is not present");;
                    return response;
                }
            case VOTER_ID:
                if (reqObj.getEpicNumber() != null && reqObj.getName() != null) {

                    KarzaVoterRequestHelper karzaVoterRequest = createVoterRequest(reqObj);

                    response = karzaVoterService.postValidation(karzaVoterRequest, reqObj.getUserId());
                    return response;
                } else {
                    response = new KarzaResponse<KarzaResultPassportNumberResponseHelper>();

                    response.setMessage("Name or voter id not present");;
                    return response;
                }
            case PAN:
                if (reqObj.getPanNumber() != null && reqObj.getName() != null) {

                    KarzaPANHelperVO karzapanRequest = createPanRequest(reqObj);

                    response = karzaPanService.postValidation(karzapanRequest, reqObj.getUserId());
                    return response;
                } else {
                    response = new KarzaResponse<KarzaResultPassportNumberResponseHelper>();

                    response.setMessage("Name or passport number is not present");;
                    return response;
                }
            default:
        }

        return response;
    }

    private KarzaDLHelperVO createDLRequest(KycRequest reqObj) {
        KarzaDLHelperVO request = new KarzaDLHelperVO();

        request.setDlNumber(reqObj.getDlNumber());
        request.setDateOfBirth(reqObj.getDob());

        return request;
    }

    private KarzaPassportHelperVO createPassportRequest(KycRequest reqObj) {
        KarzaPassportHelperVO request = new KarzaPassportHelperVO();
        request.setPassportNumber(reqObj.getPassportNumber());
        request.setDateOfBirth(reqObj.getDob());

        return request;
    }

    private KarzaVoterRequestHelper createVoterRequest(KycRequest reqObj) {
        KarzaVoterRequestHelper request = new KarzaVoterRequestHelper();

        request.setEpicNumber(reqObj.getEpicNumber());
        request.setName(reqObj.getName());

        return request;
    }

    private KarzaPANHelperVO createPanRequest(KycRequest reqObj) {
        KarzaPANHelperVO request = new KarzaPANHelperVO();

        request.setPanNumber(reqObj.getPanNumber());
        request.setName(reqObj.getName());

        return request;
    }
}
