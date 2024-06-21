package com.coinmarketcap.jsonResponse;

import java.util.ArrayList;
import java.util.Date;

public class ETH {
	 public int id;
	    public String name;
	    public String symbol;
	    public String slug;
	    public int num_market_pairs;
	    public Date date_added;
	    public ArrayList<String> tags;
	    public Object max_supply;
	    public double circulating_supply;
	    public double total_supply;
	    public int is_active;
	    public boolean infinite_supply;
	    public Object platform;
	    public int cmc_rank;
	    public int is_fiat;
	    public Object self_reported_circulating_supply;
	    public Object self_reported_market_cap;
	    public Object tvl_ratio;
	    public Date last_updated;
	    public Quote quote;
}
