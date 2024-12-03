package com.toucan.tourtptrs.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpiPinDto implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = -7025728527820413389L;

	@Id
	private String id;
    
    private Integer upiPin;
	
    private String accNum;
    
    private String user;
}
