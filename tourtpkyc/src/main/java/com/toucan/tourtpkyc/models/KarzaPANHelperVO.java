package com.toucan.tourtpkyc.models;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class KarzaPANHelperVO implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -9047471841051907636L;

	private String consent = "Y";

	private String panNumber;

	private String name;

}
