package com.coinmarketcap.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinmarketcap.component.UserSession;
import com.coinmarketcap.dto.UpdateUserDTO;
import com.coinmarketcap.dto.UserDetailDTO;
import com.coinmarketcap.entity.JsonResponse;
import com.coinmarketcap.jsonResponse.JsonResponseToUser;
import com.coinmarketcap.jsonResponse.Root;
import com.coinmarketcap.model.JwtRequest;
import com.coinmarketcap.model.JwtResponse;
import com.coinmarketcap.services.ExternalAPIService;
import com.coinmarketcap.services.JsonResponseService;
import com.coinmarketcap.services.UserDetailService;
import com.coinmarketcap.utils.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private JsonResponseToUser jsonResponseToUser;

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
		 userSession.setUserName( this.helper.getUsernameFromToken(token));
		 userSession.setId(1L);
		JwtResponse response = new JwtResponse(token, request.getUserName());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody UpdateUserDTO user) {
		user.setUserName(userSession.getUserName());
		ResponseEntity<Object> userSaved = userDetailsService.updateUser(user);
		return new ResponseEntity<UserDetailDTO>(HttpStatus.ACCEPTED);

	}

	@GetMapping("/getCoin/{req1}")
	public ResponseEntity<?> getCoin(@PathVariable (value="req1") String req1) {

		String response = externalAPIService.callExternalAPI(req1);
		try {
			JsonResponse savedResponse = jsonResponseService.saveJsonResponse(response);
			ObjectMapper obj = new ObjectMapper();
			Root root = obj.readValue(response, Root.class);
			Map<String,List<String>> getCoinResponse = new HashMap<String,List<String>>();
			if(null!=root.data.bTC) {
				jsonResponseToUser.setName(root.data.bTC.name);
				jsonResponseToUser.setSymbol(root.data.bTC.symbol);
				jsonResponseToUser.setPrice(root.data.bTC.quote.uSD.price);
				List<String> btcdata = new ArrayList<String>();
				btcdata.add(jsonResponseToUser.getName());
				btcdata.add(jsonResponseToUser.getPrice());
				getCoinResponse.put(jsonResponseToUser.getSymbol(), btcdata);
			}
//			if(null!=root.data.eTH) {
//				jsonResponseToUser.setName(root.data.eTH.name);
//				jsonResponseToUser.setSymbol(root.data.eTH.symbol);
//				jsonResponseToUser.setPrice(root.data.eTH.quote.uSD.price);
//				List<String> btcdata = new ArrayList<String>();
//				btcdata.add(jsonResponseToUser.getName());
//				btcdata.add(jsonResponseToUser.getPrice());
//				getCoinResponse.put(jsonResponseToUser.getSymbol(), btcdata);
//			}
			return ResponseEntity.ok(getCoinResponse.toString());
//			return new ResponseEntity<UserDetailDTO>(HttpStatus.ACCEPTED);
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
