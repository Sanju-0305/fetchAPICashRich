package com.coinmarketcap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDetailDTO {
	private Long id;

	@NotEmpty
	private String firstName;
	
	private String lastName;
	
	@Email
	private String email;
	
	@NotEmpty
	@Size(max=13 ,message = "Mobile No should be in range max 13")
	private String mobile;
	
	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9]*$")
	@Size(min =4,max=15 ,message = "Username should be in range 4 to 15")
	private String userName;
	
	@NotEmpty
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	@Size(min =8,max=15,message = "Password should be in range 4 to 15")
	private String password;

	public UserDetailDTO() {
		
	}
	public UserDetailDTO(Long id, String firstName, String lastName, String email, String mobile, String userName,
			String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.userName = userName;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
