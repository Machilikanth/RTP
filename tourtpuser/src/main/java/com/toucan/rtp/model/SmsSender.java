package com.toucan.rtp.model;

import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class SmsSender {
	public static final String USERNAME = "username=";
	public static final String PASSWORD = "&password=";
	public static final String TYPE = "&type=";
	public static final String DLR = "&dlr=";
	public static final String DESTINATION = "&destination=";
	public static final String SOURCE = "&source=";
	public static final String MSG = "&message=";
	public static final String ENTITYID = "&entityid=";
	public static final String TEMPID = "&tempid=";
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String STAR = "*";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String NO_CACHE = "no-cache";
 
	 public static void main(String[] args) {
//		 try {
//			 
//				URL sendUrl = new URL("https://rslri.connectbind.com:8443/bulksms/bulksms?" + USERNAME +"di78-trans"+ PASSWORD + "digi789"
//						+ TYPE + "0" + DLR + "1" + DESTINATION
//						+ "8886846963" + SOURCE + "DIGIML" + MSG + "Dear User, Your one time password 4567865 and its valid for 15 mins. Do not share to anyone. Digimiles."
//						+ ENTITYID + "1201159100989151460" + TEMPID + "1107162089216820716");
//				System.out.println(sendUrl);
//				RestTemplate restTemplate = new RestTemplate();
//				String responseEntity = restTemplate.postForObject(sendUrl.toString(), null, String.class);
//				if (responseEntity != null) {
//					System.out.println("RESPONSE->>>>"+responseEntity);
//					}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
		}
			
//	public Boolean submitMessage() {
//		String message ="" ;
//		
// 
//		
//			
//	}
//	https://rslri.connectbind.com:8443/bulksms/bulksms?username=di78-trans&password=digi789&type=0&dlr=1&destination=9916011355&source=DIGIML&message=
	
//	 Dear User, Your one time password 987654 and its valid for 15 mins. Do not share to anyone. Digimiles.&entityid=1201159100989151460&tempid=1107162089216820716
//http://sms.digimiles.in/

}
