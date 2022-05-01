package com.huyhoang.covid19.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huyhoang.covid19.dao.LikesDAO;
import com.huyhoang.covid19.services.LikesService;

@Service
@Transactional
public class LikeServiceImpl implements LikesService {
	@Autowired
	private LikesDAO likesDAO;
	
	public Boolean likePost(String username, Integer id_post) {
		return likesDAO.likePost(username, id_post);
	}
	
	public Boolean unlikePost(String username, Integer id_post) {
		return likesDAO.unlikePost(username, id_post);
	}
}
