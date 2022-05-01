package com.huyhoang.covid19.services;



public interface LikesService {

	public Boolean likePost(String username, Integer id_post);
	
	public Boolean unlikePost(String username, Integer id_post);
	
}
