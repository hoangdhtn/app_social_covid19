package com.huyhoang.covid19.services;

import java.util.List;

import com.huyhoang.covid19.entities.Likes;

public interface LikesService {

	public Boolean likePost(String username, Integer id_post);
	
	public Boolean unlikePost(String username, Integer id_post);
	
	public List<Likes> getLikePost(String username, Integer id_post);
	
}
