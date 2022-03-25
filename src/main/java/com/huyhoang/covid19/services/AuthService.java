package com.huyhoang.covid19.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huyhoang.covid19.dao.AuthDAO;
import com.huyhoang.covid19.entities.Users;

@Service
@Transactional
public class AuthService {
	
	@Autowired
	private AuthDAO authDAO;
	
	public Users loadUserByUsername(final String username) {
		return authDAO.loadUsername(username);
	}
	
	public boolean checkLogin(Users userForm) {
		return authDAO.checkLogin(userForm);
	}
	
	public Users signupUser(Users data) {
		return authDAO.signupUser(data);
	}
}
