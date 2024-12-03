package com.toucan.tourtptrs.dto;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfBanksDto implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = -5758893022259788589L;

	@Id
	private String id;
	
    private ArrayList<String> listOfBanks;
}
