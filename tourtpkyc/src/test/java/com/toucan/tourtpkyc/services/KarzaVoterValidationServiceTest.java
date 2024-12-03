package com.toucan.tourtpkyc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;

import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.KarzaVoterRequestHelper;
import com.toucan.tourtpkyc.models.KarzaVoterResponseResultHelper;
import com.toucan.tourtpkyc.services.KarzaVoterValidationService;

public class KarzaVoterValidationServiceTest {
	@Mock
	KarzaVoterValidationService karzaVoterValidationService = new KarzaVoterValidationService();

	private KarzaVoterRequestHelper karzaVoterHelperVO;

	private static final String  epic = "879654133";
	private static final String name = "JOHN DOE";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testpostValidation() {
		karzaVoterHelperVO = new KarzaVoterRequestHelper();

		karzaVoterHelperVO.setEpicNumber(epic);;
		karzaVoterHelperVO.setName(name);

		KarzaResponse<KarzaVoterResponseResultHelper> karzaResponse = new KarzaResponse<KarzaVoterResponseResultHelper>();
		karzaResponse.setMessage("kyc verified");
		karzaResponse.setStatusCode(HttpStatusCode.valueOf(101));

		when(karzaVoterValidationService.postValidation(karzaVoterHelperVO, "1234")).thenReturn(karzaResponse);
		assertEquals(karzaResponse, karzaVoterValidationService.postValidation(karzaVoterHelperVO, "1234"));


	}
}
