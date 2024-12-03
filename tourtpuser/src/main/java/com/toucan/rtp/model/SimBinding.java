package com.toucan.rtp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Simbinding")
public class SimBinding {
	
//	private String id;
	private String MSISDN;
	private String ICCID;
	private String IMSI;
	private String IMEI;

}
