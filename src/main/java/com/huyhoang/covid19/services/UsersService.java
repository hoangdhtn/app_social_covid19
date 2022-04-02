package com.huyhoang.covid19.services;

import java.util.List;

import com.huyhoang.covid19.dao.UsersDAO;
import com.huyhoang.covid19.entities.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UsersService {

	@Autowired
	private UsersDAO usersDAO;
	
	public Users getUsers(Integer id) {
		return usersDAO.getUser(id);
	}
	
	public Users addUsers(Users data) {
		return usersDAO.addUser(data);
	}
	
	public Users updateUsers(String username, Users data, MultipartFile[] files) {
		return usersDAO.updateUser(username, data, files);
	}
	
	public boolean deleteUser(Integer id) {
		return usersDAO.deleteUser(id);
	}
	
	public List<Users> getAllUsers(){
		return usersDAO.getAllUsers();
	}
	
	public boolean userFollow(Users users, Integer id_follow) {
		return usersDAO.userFollow(users, id_follow);
	}
	
	public boolean userUnFollow(Users users, Integer id_follow) {
		return usersDAO.userUnFollow(users, id_follow);
	}
	
}

