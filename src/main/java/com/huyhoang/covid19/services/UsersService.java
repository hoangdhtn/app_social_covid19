package com.huyhoang.covid19.services;

import java.util.List;

import com.huyhoang.covid19.dao.UsersDAO;
import com.huyhoang.covid19.entities.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


public interface UsersService {


	public Users getUsers(Integer id);
	
	public Users addUsers(Users data);
	
	public Users updateUsers(String username, Users data, MultipartFile[] files);
	
	public boolean deleteUser(Integer id);
	
	public List<Users> getAllUsers();
	
	public boolean userFollow(Users users, Integer id_follow);
	
	public boolean userUnFollow(Users users, Integer id_follow);
	
	public Users getUserByToken(String username);
	
}

