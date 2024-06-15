package com.coinmarketcap.entity;

import java.util.Collection;

import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userDetails")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@NaturalId
	@Column(name = "email")
	private String email;
	@Column(name = "mobile")
	private String mobile;
	@NaturalId
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;

	@Autowired
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Autowired
	public String getUsername() {
		return userName;
	}

	@Autowired
	public boolean isAccountNonExpired() {
		return true;
	}

	@Autowired
	public boolean isAccountNonLocked() {
		return true;
	}

	@Autowired
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Autowired
	public boolean isEnabled() {
		return true;
	}

	public User() {

	}

	public User(String firstName, String lastName, String email, String mobile, String userName, String password) {
		super();
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

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobile=" + mobile + ", userName=" + userName + ", password=" + password + "]";
	}

}
