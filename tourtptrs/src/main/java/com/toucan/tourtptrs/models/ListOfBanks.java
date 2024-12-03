package com.toucan.tourtptrs.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "listofbanks")
public class ListOfBanks implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 2258110141359798406L;

	@Id
	private String id;
	
    private ArrayList<String> listOfBanks;

}

