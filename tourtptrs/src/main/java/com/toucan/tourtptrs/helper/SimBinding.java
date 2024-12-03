package com.toucan.tourtptrs.helper;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Simbinding")
public class SimBinding implements Serializable{
	
	private String MSISDN;
	private String ICCID;
	private String IMSI;
	private String IMEI;


}