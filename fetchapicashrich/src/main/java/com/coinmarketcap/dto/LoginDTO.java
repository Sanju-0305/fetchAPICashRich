package com.coinmarketcap.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDTO {
	@NotEmpty
	@Size(min =4,max=15 ,message = "Username should be in range 4 to 15")
	private String userName;
	
	@NotEmpty
//	@Pattern(regexp="")
	@Size(min =8,max=15,message = "Password should be in range 4 to 15")
	private String password;
	
	public LoginDTO() {
		
	}

	public LoginDTO(@NotEmpty @Size(min = 4, max = 15, message = "Username should be in range 4 to 15") String userName,
			@NotEmpty @Size(min = 8, max = 15, message = "Password should be in range 4 to 15") String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
