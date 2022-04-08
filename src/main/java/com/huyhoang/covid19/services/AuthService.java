package com.huyhoang.covid19.services;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huyhoang.covid19.dao.AuthDAO;
import com.huyhoang.covid19.entities.ResetPassword;
import com.huyhoang.covid19.entities.Users;

@Service
@Transactional
public class AuthService {
	
	@Autowired
	private AuthDAO authDAO;
	
	public Users loadUserByUsername(String username) {
		return authDAO.loadUsername(username);
	}
	
	public boolean checkLogin(Users userForm) {
		return authDAO.checkLogin(userForm);
	}
	
	public Users signupUser(Users data) {
		return authDAO.signupUser(data);
	}
	
	public Boolean resetPassword(ResetPassword rPassword) {
		return authDAO.resetPassword(rPassword);
	}
}
