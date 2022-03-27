package com.huyhoang.covid19.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
