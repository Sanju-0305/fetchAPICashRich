package com.coinmarketcap.jsonResponse;

import org.springframework.stereotype.Component;

@Component
public class JsonResponseToUser {

	private String name;
	private String symbol;
	private String price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price.toString();
	}
	
	
}
