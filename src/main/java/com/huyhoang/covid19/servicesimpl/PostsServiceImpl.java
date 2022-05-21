package com.huyhoang.covid19.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.dao.PostsDAO;
import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Posts_Cmt;
import com.huyhoang.covid19.services.PostsService;

@Service
@Transactional
public class PostsServiceImpl implements PostsService {
	@Autowired
	private PostsDAO postsDAO;
	
	public List<Posts> getAllPosts(int position, int pageSize){
		return postsDAO.getAllPosts( position,  pageSize);
	}
	
	public List<Posts> getAllPostsUser(Integer id_user){
		return postsDAO.getAllPostsUser(id_user);
	}
	
	public List<Posts> getAllPostsInWall(Integer id_user){
		return postsDAO.getAllPostsInWall(id_user);
	}
	
	public Posts addPost(String username, Posts data, MultipartFile[] files) {
		return postsDAO.addPost(username, data, files);
	}
	
	public Posts updatePost(String username, Posts postForm) {
		return postsDAO.updatePost(username, postForm);
	}
	
	public Boolean deletePost(String username, Integer id_post) {
		return postsDAO.deletePost(username, id_post);
	}
	
	public List<Posts_Cmt> getCommentPost(Integer id_post){
		return postsDAO.getCommentPost(id_post);
	}
	
	public Boolean addCommentPost(String username, Integer id_post, Posts_Cmt data) {
		return postsDAO.addCommentPost(username, id_post, data);
	}
	
	public Boolean deleteCommentPost(String username, Integer id_cmt) {
		return postsDAO.deleteCommentPost(username, id_cmt);
	}
}
