package com.toucan.tourtpkyc.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AesEncryptDecrypt {
	
	String keyString = "d60bf69a76bde643wq34eresr43rw3es";

	byte[] keyBytes = keyString.getBytes();

	SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");

	public String encrypt(String strToEncrypt) {
		String encryptedStr = "";
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] encryptedTextBytes = cipher.doFinal(strToEncrypt.getBytes());

			encryptedStr = Base64.getEncoder().encodeToString(encryptedTextBytes);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}

		return encryptedStr;

	}
	
	public String decrypt(String strToDecrypt) {
		String decyptedStr = "";
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedTextBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));

			 decyptedStr = new String(decryptedTextBytes);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}

		return decyptedStr;
		
	}


}
