package com.toucan.rtp.core.service;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AESKeyGenerator {

//	// Method to generate a random AES key
//	public static SecretKey generateAESKey() throws Exception {
//		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//		keyGen.init(256); // AES key size (128, 192, or 256 bits)
//		System.out.println("The Key:"+keyGen.generateKey());
//		return keyGen.generateKey();
//	}

	
	
	// Method to encrypt a string using AES
	public static String encrypt(String data, String AESKEY) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(AESKEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encryptedBytes = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes); // Convert to Base64 for easier readability
	}

	// Method to decrypt a string using AES
	public static String decrypt(String encryptedData, String AESKEY) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(AESKEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
		byte[] decryptedBytes = cipher.doFinal(decodedBytes);
		return new String(decryptedBytes);
	}


}
