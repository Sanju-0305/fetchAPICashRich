package com.coinmarketcap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coinmarketcap.entity.User;
import com.coinmarketcap.repository.UserDetailsRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Load user from database
		User user = new User();
		try {
         user = userDetailsRepository.findFirstByEmail(username);
         if(null==user) {
        	 throw new UsernameNotFoundException("User Not Found");
         }
        }
		catch (Exception e) {
			throw new UsernameNotFoundException("User Not Found");
		}
        return user;
    }
}
