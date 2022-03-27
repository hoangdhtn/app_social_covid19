package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.PostsService;

@RestController
@RequestMapping("/api")
public class PostsController {

	@Autowired
	private PostsService postsService;

	// Get all post
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

	// Get all post user
	@RequestMapping(value = "/posts/{id_user}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Posts>> getAllPostsUser(@PathVariable(name = "id_user") Integer id_user,
			@RequestBody Users user) {

		HttpStatus httpStatus = null;

		List<Posts> list = null;
		try {
			if (id_user == user.getId()) {
				list = postsService.getAllPostsInWall(id_user);
				httpStatus = HttpStatus.OK;
			} else if (id_user != user.getId()) {
				list = postsService.getAllPostsUser(id_user);
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<List<Posts>>(list, httpStatus);
	}

	// Add post, sử dụng form data bởi vì cần upload file img
	@RequestMapping(value = "/posts/{id_user}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<String> addPost(@PathVariable("id_user") Integer id_user, @ModelAttribute Posts data,
			@RequestParam("files") MultipartFile[] files) {
		HttpStatus httpStatus = null;
		String result = "";

		// Add medical
		try {
			if (postsService.addPost(id_user, data, files) != null) {
				result = "Add post success";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Add post fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}
}
