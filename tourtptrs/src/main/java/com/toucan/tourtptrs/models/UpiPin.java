package com.toucan.tourtptrs.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "UpiPin")
public class UpiPin implements Serializable{
	
	@JsonIgnore
	private static final long serialVersionUID = -1595389701836234194L;

	@Id
	private String id;
    
    private Integer upiPin;
    
    private String accNum;

    @Transient
    private String user;
}
