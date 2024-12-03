package com.toucan.tourtpkyc.models;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class KarzaDLHelperVO implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -1591012412555777612L;

	private String dateOfBirth;

	private String dlNumber;

}
