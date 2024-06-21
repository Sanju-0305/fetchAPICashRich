package com.coinmarketcap.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalAPIService {
	private WebClient webClient;
	@Value("${myapp.api.url}")
	private String apiUrl;

	@Value("${myapp.api.key}")
	private String apiKey;
	
	@Value("${myapp.api.value}")
	private String apival;

	public ExternalAPIService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://api.example.com").build();
	}

	public String callExternalAPI(String req1) {
		String response = this.webClient.get().uri(apiUrl+req1)
				.header(apiKey, apival).retrieve().bodyToMono(String.class)
				.block();
		return response;
	}
}
