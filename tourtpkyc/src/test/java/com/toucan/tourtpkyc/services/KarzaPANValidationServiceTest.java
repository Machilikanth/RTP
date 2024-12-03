package com.toucan.tourtpkyc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;

import com.toucan.tourtpkyc.models.KarzaPANHelperVO;
import com.toucan.tourtpkyc.models.KarzaPANValidationResponseResult;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.services.KarzaPANValidationService;

public class KarzaPANValidationServiceTest {
	@Mock
	KarzaPANValidationService karzaPanValidationService = new KarzaPANValidationService();

	private KarzaPANHelperVO karzaPANHelperVO;

	private static final String name = "JOHN DOE";
	private static final String dob = "QWEP34RE5T";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testpostValidation() {
		karzaPANHelperVO = new KarzaPANHelperVO();
		karzaPANHelperVO.setName(name);
		karzaPANHelperVO.setPanNumber(dob);

		KarzaResponse<KarzaPANValidationResponseResult> karzaResponse = new KarzaResponse<KarzaPANValidationResponseResult>();
		karzaResponse.setMessage("kyc verified");
		karzaResponse.setStatusCode(HttpStatusCode.valueOf(101));

		when(karzaPanValidationService.postValidation(karzaPANHelperVO, "1234")).thenReturn(karzaResponse);
		assertEquals(karzaResponse, karzaPanValidationService.postValidation(karzaPANHelperVO, "1234"));


	}

}
