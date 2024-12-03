package com.toucan.rtp.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "RSA_KEY")
public class RSA_KEY {
	@Id
	private String id;
	private String privateKey;
	private String publicKey;
}
