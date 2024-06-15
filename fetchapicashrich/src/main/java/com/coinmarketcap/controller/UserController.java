package com.coinmarketcap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinmarketcap.dto.UserDetailDTO;
import com.coinmarketcap.entity.JsonResponse;
import com.coinmarketcap.model.JwtRequest;
import com.coinmarketcap.model.JwtResponse;
import com.coinmarketcap.services.ExternalAPIService;
import com.coinmarketcap.services.JsonResponseService;
import com.coinmarketcap.services.UserDetailService;
import com.coinmarketcap.utils.JwtHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private UserDetailService userDetailsService;

	@Lazy
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private ExternalAPIService externalAPIService;

	 @Autowired
	    private JsonResponseService jsonResponseService;
//	 @Autowired
//	 private JwtResponse jwtResponse;

	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDetailDTO user) {

		if (userDetailsService.hasUserWithEmail(user.getEmail())) {
			return new ResponseEntity<>("User already exists with this email", HttpStatus.NOT_ACCEPTABLE);
		}
		if (userDetailsService.existingSameUserName(user.getUserName())) {
			return new ResponseEntity<>("User already exists with this user Name", HttpStatus.NOT_ACCEPTABLE);
		}
		UserDetailDTO userSaved = userDetailsService.addUser(user);
		return new ResponseEntity<UserDetailDTO>(userSaved, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		this.doAuthenticate(request.getUserName(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
		String token = this.helper.generateToken(userDetails);
		JwtResponse response = new JwtResponse(token, request.getUserName());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody UserDetailDTO user) {
		if (userDetailsService.existingSameUserName(user.getUserName())) {
			UserDetailDTO userSaved = userDetailsService.updateUser(user);
			return new ResponseEntity<UserDetailDTO>(userSaved, HttpStatus.CREATED);			
		}
		return new ResponseEntity<>("User does not exists with this user Name", HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@GetMapping("/getCoin")
	public ResponseEntity<?> getCoin() {
		
		String response = externalAPIService.callExternalAPI();
		try {
            JsonResponse savedResponse = jsonResponseService.saveJsonResponse(response);
            return ResponseEntity.ok("Json response saved with ID: " + savedResponse.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving JSON response: " + e.getMessage());
        }
		
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

}
