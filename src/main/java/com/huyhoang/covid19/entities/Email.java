package com.huyhoang.covid19.entities;

public class Email {
	String status;
	String email;
	
	public Email() {
		// TODO Auto-generated constructor stub
	}

	public Email(String status, String email) {
		this.status = status;
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
