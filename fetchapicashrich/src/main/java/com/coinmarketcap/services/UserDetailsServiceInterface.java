package com.coinmarketcap.services;

import com.coinmarketcap.dto.UserDetailDTO;

public interface UserDetailsServiceInterface {

	UserDetailDTO addUser(UserDetailDTO user);

	boolean hasUserWithEmail(String email);
	
//	boolean hasUserWithEmail(String email);
	
	

}
