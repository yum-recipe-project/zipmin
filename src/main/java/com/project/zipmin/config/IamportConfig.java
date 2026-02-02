package com.project.zipmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siot.IamportRestClient.IamportClient;
	
@Configuration
public class IamportConfig {
	
	@Bean
	public IamportClient iamportClient(
			@Value("${iamport.api-key}") String apiKey,
			@Value("${iamport.api-secret}") String apiSecret) {

		return new IamportClient(apiKey, apiSecret);
	}
	
}
