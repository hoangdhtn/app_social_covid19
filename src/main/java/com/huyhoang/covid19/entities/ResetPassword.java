package com.huyhoang.covid19.entities;

public class ResetPassword {

	String key;
	String email;
	String passwordNew;
	String status;
	
	public ResetPassword() {
		
	}

	public ResetPassword(String key, String email, String passwordNew) {
		this.key = key;
		this.email = email;
		this.passwordNew = passwordNew;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
