package com.huyhoang.covid19.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huyhoang.covid19.dao.AuthDAO;
import com.huyhoang.covid19.entities.ResetPassword;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.AuthService;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	
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
