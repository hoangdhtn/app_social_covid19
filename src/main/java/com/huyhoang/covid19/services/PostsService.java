package com.huyhoang.covid19.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Posts_Cmt;


public interface PostsService {


	public List<Posts> getAllPosts(int position, int pageSize);
	
	public List<Posts> getAllPostsUser(Integer id_user);
	
	public List<Posts> getAllPostsInWall(Integer id_user);
	
	public Posts addPost(String username, Posts data, MultipartFile[] files);
	
	public Posts updatePost(String username, Posts postForm);
	
	public Boolean deletePost(String username, Integer id_post);
	
	public List<Posts_Cmt> getCommentPost(Integer id_post);
	
	public Boolean addCommentPost(String username, Integer id_post, Posts_Cmt data);
	
	public Boolean deleteCommentPost(String username, Integer id_cmt);
}
