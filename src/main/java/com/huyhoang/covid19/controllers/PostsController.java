package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.services.PostsService;

@RestController
@RequestMapping("/api")
public class PostsController {

	@Autowired
	private PostsService postsService;
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Posts>> getAllPosts() {

		HttpStatus httpStatus = null;

		List<Posts> list = null;
		try {
			if (postsService.getAllPosts() != null) {
				list = postsService.getAllPosts();
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<Posts>>(list, httpStatus);
	}
}
