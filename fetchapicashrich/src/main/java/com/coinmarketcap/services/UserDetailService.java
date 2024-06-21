package com.coinmarketcap.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinmarketcap.dto.LoginDTO;
import com.coinmarketcap.dto.UpdateUserDTO;
import com.coinmarketcap.dto.UserDetailDTO;
import com.coinmarketcap.entity.User;
import com.coinmarketcap.repository.UserDetailsRepository;

import jakarta.validation.Valid;

@Service
public class UserDetailService implements UserDetailsServiceInterface {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetailDTO addUser(UserDetailDTO user) {
		User userDetail = new User();
		userDetail.setFirstName(user.getFirstName());
		userDetail.setLastName(user.getLastName());
		userDetail.setEmail(user.getEmail());
		userDetail.setMobile(user.getMobile());
		userDetail.setUserName(user.getUserName());
		userDetail.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUserDetail = userDetailsRepository.save(userDetail);
		UserDetailDTO savedUserDetail1 = new UserDetailDTO();
		savedUserDetail1.setId(savedUserDetail.getId());
				return savedUserDetail1;
	}

	
	public boolean hasUserWithEmail(String email) {
		int userCount = userDetailsRepository.findbyEmail(email);
			
		return userCount>0?true:false;
	}
	
	public boolean existingSameUserName(String userName) {
		int userCount = userDetailsRepository.findbyUserName(userName);
			
		return userCount>0?true:false;
	}


	public String login(@Valid LoginDTO loginDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	public UserDetailDTO getByEmail(String email) {
		// TODO Auto-generated method stub
		UserDetailDTO userDetail = userDetailsRepository.findbyEmail1(email);
		return userDetail;
	}


	public User loadUserByUsername(String email) {
		User userDetail = userDetailsRepository.loadUserByUsername(email);
		return userDetail;
	}


	public ResponseEntity<Object> updateUser(@Valid UpdateUserDTO user) {
		User userDetail = new User();
		
		Optional<User> userOptional = Optional.ofNullable(userDetailsRepository.findFirstByEmail(user.getUserName()));

		if (userOptional.isEmpty())
			return ResponseEntity.notFound().build();

		userDetail.setId(userOptional.get().getId());
		userDetail.setFirstName(user.getFirstName());
		userDetail.setLastName(user.getLastName());
		userDetail.setMobile(user.getMobile());
		userDetail.setEmail(userOptional.get().getEmail());
		userDetail.setUserName(userOptional.get().getUserName());
		userDetail.setPassword(passwordEncoder.encode(user.getPassword()));
		userDetailsRepository.save(userDetail);
		return ResponseEntity.noContent().build();
	}


}
