package com.huyhoang.covid19.entities;

public class UserAndToken {
	Users user;
	String token;
	
	public UserAndToken() {
		
	}

	public UserAndToken(Users user, String token) {
		this.user = user;
		this.token = token;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	
	
}
