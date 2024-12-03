package com.toucan.tourtpkyc.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import lombok.Data;

@Data
public class KarzaVoterResponseResultHelper implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -8751778550291693923L;

	@JsonProperty("ps_lat_long")
	@JsonInclude(Include.NON_NULL)
	private String psLatLong;

	@JsonProperty("rln_name_v1")
	@JsonInclude(Include.NON_NULL)
	private String rlnNameV1;

	@JsonProperty("rln_name_v2")
	@JsonInclude(Include.NON_NULL)
	private String rlnNameV2;

	@JsonProperty("rln_name_v3")
	@JsonInclude(Include.NON_NULL)
	private String rlnNameV3;

	@JsonProperty("part_no")
	@JsonInclude(Include.NON_NULL)
	private String partNo;

	@JsonProperty("rln_type")
	@JsonInclude(Include.NON_NULL)
	private String rlnType;

	@JsonProperty("section_no")
	@JsonInclude(Include.NON_NULL)
	private String sectionNo;

	@JsonProperty("id")
	@JsonInclude(Include.NON_NULL)
	private String id;

	@JsonProperty("epic_no")
	@JsonInclude(Include.NON_NULL)
	private String epicNo;

	@JsonProperty("rln_name")
	@JsonInclude(Include.NON_NULL)
	private String rlnName;

	@JsonProperty("district")
	@JsonInclude(Include.NON_NULL)
	private String district;

	@JsonProperty("last_update")
	@JsonInclude(Include.NON_NULL)
	private String lastUpdate;

	@JsonProperty("state")
	@JsonInclude(Include.NON_NULL)
	private String state;

	@JsonProperty("ac_no")
	@JsonInclude(Include.NON_NULL)
	private String acNo;

	@JsonProperty("slno_inpart")
	@JsonInclude(Include.NON_NULL)
	private String slNoInpart;

	@JsonProperty("ps_name")
	@JsonInclude(Include.NON_NULL)
	private String psName;

	@JsonProperty("pc_name")
	@JsonInclude(Include.NON_NULL)
	private String pcName;

	@JsonProperty("house_no")
	@JsonInclude(Include.NON_NULL)
	private String houseNo;

	@JsonProperty("name")
	@JsonInclude(Include.NON_NULL)
	private String name;

	@JsonProperty("part_name")
	@JsonInclude(Include.NON_NULL)
	private String partName;

	@JsonProperty("st_code")
	@JsonInclude(Include.NON_NULL)
	private String st_code;

	@JsonProperty("gender")
	@JsonInclude(Include.NON_NULL)
	private String gender;

	@JsonProperty("age")
	@JsonInclude(Include.NON_NULL)
	private String age;

	@JsonProperty("ac_name")
	@JsonInclude(Include.NON_NULL)
	private String acName;

	@JsonProperty("name_v1")
	@JsonInclude(Include.NON_NULL)
	private String nameV1;

	@JsonProperty("dob")
	@JsonInclude(Include.NON_NULL)
	private String dob;

	@JsonProperty("name_v3")
	@JsonInclude(Include.NON_NULL)
	private String nameV3;

	@JsonProperty("name_v2")
	@JsonInclude(Include.NON_NULL)
	private String nameV2;

	@JsonIgnore
	public String getJson() {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
