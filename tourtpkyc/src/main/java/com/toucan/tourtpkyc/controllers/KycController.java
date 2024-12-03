package com.toucan.tourtpkyc.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.toucan.tourtpkyc.services.KycVerificationService;
import com.toucan.tourtpkyc.models.KarzaResponse;
import com.toucan.tourtpkyc.models.KycRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class KycController{

	@Autowired
	KycVerificationService kycService;


	@PostMapping("/kycverification")
	public KarzaResponse kycVerification(@Valid @RequestBody KycRequest entity) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		KarzaResponse result = kycService.postKycVerification(entity);
		
		
		
		return result;
	}


}
