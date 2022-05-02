package com.huyhoang.covid19.services;



import com.huyhoang.covid19.entities.ResetPassword;
import com.huyhoang.covid19.entities.Users;


public interface AuthService {
	
	
	public Users loadUserByUsername(String username);
	
	public boolean checkLogin(Users userForm);
	
	public Users signupUser(Users data);
	
	public Boolean resetPassword(ResetPassword rPassword);
}
