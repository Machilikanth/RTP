package com.toucan.tourtpkyc.models;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class KarzaPassportHelperVO implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -3568151195001998645L;

	private String passportNumber;

	private String dateOfBirth;

}
