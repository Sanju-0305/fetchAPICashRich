package com.coinmarketcap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalAPIService {
	 private WebClient webClient;
	    
	    @Autowired
	    public ExternalAPIService(WebClient.Builder webClientBuilder) {
	        this.webClient = webClientBuilder.baseUrl("https://api.example.com").build();
	    }
	    
	    public String callExternalAPI() {
	        String apiUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETHLTC";
	        
	        String response = webClient.get()
	            .uri(apiUrl)
	            .header("X-CMC_PRO_API_KEY", "27ab17d1-215f-49e5-9ca4-afd48810c149")
	            .retrieve()
	            .bodyToMono(String.class)
	            .block();  
	        
	        System.out.println("Response: " + response);
	        return response;
	    }
}
