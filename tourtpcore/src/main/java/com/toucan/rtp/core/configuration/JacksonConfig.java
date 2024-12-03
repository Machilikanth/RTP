package com.toucan.rtp.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class JacksonConfig {
	
	@Bean
	public ObjectMapper buildMapper() {
			ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
					.registerModule(new Jdk8Module());
			JavaTimeModule javaTimeModule = new JavaTimeModule();
			objectMapper.registerModule(javaTimeModule);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			return objectMapper;
		
		}

}
