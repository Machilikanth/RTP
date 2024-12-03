package com.toucan.rtp.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimBinding implements Serializable{

	private static final long serialVersionUID = 3862456889546163373L;
	    @JsonProperty("MSISDN")
	    private String MSISDN;

	    @JsonProperty("ICCID")
	    private String ICCID;

	    @JsonProperty("IMSI")
	    private String IMSI;

	    @JsonProperty("IMEI")
	    private String IMEI;
	    
	    
	    
	    private String deviceId;
	    //private String mobileId;
	    private String serviceId;
	    //private LocalDateTime TimeStamp;
	    private String sMSToken;
	    private String OSType;

}
