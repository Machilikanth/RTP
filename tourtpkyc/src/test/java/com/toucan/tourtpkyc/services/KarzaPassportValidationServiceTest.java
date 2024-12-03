package com.toucan.tourtpkyc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;

import com.toucan.tourtpkyc.models.KarzaPassportHelperVO;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.KarzaResultPassportNumberResponseHelper;
import com.toucan.tourtpkyc.services.KarzaPassportValidationService;

public class KarzaPassportValidationServiceTest {
	@Mock
	KarzaPassportValidationService karzapassportValidationService = new KarzaPassportValidationService();

	private KarzaPassportHelperVO karzaPassportHelperVO;

	private static final String passport = "879654133";
	private static final String dob = "09-08-1992";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testpostValidation() {
		karzaPassportHelperVO = new KarzaPassportHelperVO();

		karzaPassportHelperVO.setDateOfBirth(dob);
		karzaPassportHelperVO.setPassportNumber(passport);

		KarzaResponse<KarzaResultPassportNumberResponseHelper> karzaResponse = new KarzaResponse<KarzaResultPassportNumberResponseHelper>();
		karzaResponse.setMessage("kyc verified");
		karzaResponse.setStatusCode(HttpStatusCode.valueOf(101));

		when(karzapassportValidationService.postValidation(karzaPassportHelperVO, "1234")).thenReturn(karzaResponse);
		assertEquals(karzaResponse, karzapassportValidationService.postValidation(karzaPassportHelperVO, "1234"));

	}

}
