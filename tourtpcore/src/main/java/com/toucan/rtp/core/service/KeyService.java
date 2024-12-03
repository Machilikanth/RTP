package com.toucan.rtp.core.service;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toucan.rtp.core.helper.CommonResponseObject;
import com.toucan.rtp.core.model.EncryptedField;
import com.toucan.rtp.core.model.RSA_KEY;
import com.toucan.rtp.core.repository.KeyPairRepository;

@Service
public class KeyService {

    @Autowired
    private KeyPairRepository keyPairRepository;

    @Autowired
    private RSAKeyGenerator rsaKeyGenerator;

    public CommonResponseObject generateAndStoreKeyPair() throws NoSuchAlgorithmException {
        KeyPair keyPair = rsaKeyGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        RSA_KEY keyPairEntity = new RSA_KEY();
        String id = UUID.randomUUID().toString();
        keyPairEntity.setId(id);
        keyPairEntity.setPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));    
        keyPairRepository.save(keyPairEntity);

        CommonResponseObject response = new CommonResponseObject();
        response.setB_Pk(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        response.setB_id(id);

        return response;
    }

    
    public <T> T encryptData(T entity, String publicKeyString) throws Exception {
        PublicKey publicKey = getPublicKeyFromString(publicKeyString);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(EncryptedField.class)) {
                field.setAccessible(true);
                String plainData = (String) field.get(entity);

                if (plainData == null || plainData.isEmpty()) {
                    throw new IllegalArgumentException("Field " + field.getName() + " is marked as @EncryptedField but is empty or null");
                }

                try {
                    byte[] encryptedBytes = cipher.doFinal(plainData.getBytes(StandardCharsets.UTF_8));
                    String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);
                    field.set(entity, encryptedData);
                } catch (Exception e) {
                    throw new Exception("Error encrypting field " + field.getName(), e);
                }
            }
        }

        return entity;
    }

    private PublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        String publicKeyPEM = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "")
                                             .replace("-----END PUBLIC KEY-----", "")
                                             .replaceAll("\\s+", "");
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }



    public <T> T decryptData(T entity, String keyId) throws Exception {
    	if (keyId == null) {
            throw new IllegalArgumentException("Key ID must not be null");
        }
        PrivateKey privateKey = getPrivateKeyById(keyId);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(EncryptedField.class)) {
                field.setAccessible(true);
                String encryptedData = (String) field.get(entity);
                if (encryptedData == null || encryptedData.isEmpty()) {
                    throw new IllegalArgumentException("Field " + field.getName() + " is marked as @EncryptedField but is not encrypted");
                }
                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
                    byte[] decryptedBytes = cipher.doFinal(decodedBytes);
                    String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);
                    field.set(entity, decryptedData);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Field " + field.getName() + " contains invalid Base64 data");
                } catch (Exception e) {
                    throw new Exception("Error decrypting field " + field.getName());
                }
            }
        }
        //keyPairRepository.deleteById(keyId);
        return entity;
    }

    private PrivateKey getPrivateKeyById(String keyId) throws Exception {
        RSA_KEY entity = keyPairRepository.findById(keyId)
            .orElseThrow(() -> new Exception("Key not found"));
        byte[] keyBytes = Base64.getDecoder().decode(entity.getPrivateKey());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(keySpec);
    }


}
