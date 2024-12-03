package com.toucan.tourtpkyc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;

import com.toucan.tourtpkyc.models.KarzaDLHelperVO;
import com.toucan.tourtpkyc.models.KarzaDLResponseResult;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.services.KarzaDLValidationService;

class KarzaDLValidationServiceTest {

    @Mock
    KarzaDLValidationService karzadLValidationService = new KarzaDLValidationService();

    private KarzaDLHelperVO karzaDLHelperVO;

    private static final String dlNO = "879654133";
    private static final String dob = "09-08-1992";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testpostValidation() {
        karzaDLHelperVO = new KarzaDLHelperVO();

        karzaDLHelperVO.setDlNumber(dlNO);
        karzaDLHelperVO.setDateOfBirth(dob);

        KarzaResponse<KarzaDLResponseResult> karzaResponse = new KarzaResponse<KarzaDLResponseResult>();
        karzaResponse.setMessage("kyc verified");
        karzaResponse.setStatusCode(HttpStatusCode.valueOf(101));

        try {
            when(karzadLValidationService.postValidation(karzaDLHelperVO, "1234")).thenReturn(karzaResponse);
            assertEquals(karzaResponse, karzadLValidationService.postValidation(karzaDLHelperVO, "1234"));
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
