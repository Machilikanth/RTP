package com.toucan.tourtpkyc.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class KycUtils {
	
	public HttpHeaders karzaHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-karza-key", "eJFY58AdX6QG5psq");
		
		return headers;
	}

}
