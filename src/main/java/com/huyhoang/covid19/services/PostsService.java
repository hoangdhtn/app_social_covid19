package com.huyhoang.covid19.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.dao.PostsDAO;
import com.huyhoang.covid19.entities.Posts;

@Service
@Transactional
public class PostsService {

	@Autowired
	private PostsDAO postsDAO;
	
	public List<Posts> getAllPosts(){
		return postsDAO.getAllPosts();
	}
	
	public List<Posts> getAllPostsUser(Integer id_user){
		return postsDAO.getAllPostsUser(id_user);
	}
	
	public List<Posts> getAllPostsInWall(Integer id_user){
		return postsDAO.getAllPostsInWall(id_user);
	}
	
	public Posts addPost(Integer id_user, Posts data, MultipartFile[] files) {
		return postsDAO.addPost(id_user, data, files);
	}
}
