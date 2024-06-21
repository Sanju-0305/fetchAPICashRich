package com.coinmarketcap.jsonResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
	@JsonProperty("BTC") 
    public BTC bTC;
    @JsonProperty("ETH") 
    public ETH eTH;
    @JsonProperty("LTC") 
    public LTC lTC;
}
