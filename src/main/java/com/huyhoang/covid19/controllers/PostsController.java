package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Posts_Cmt;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.JwtService;
import com.huyhoang.covid19.services.PostsService;

@RestController
@RequestMapping("/api")
public class PostsController {

	@Autowired
	private PostsService postsService;
	
	@Autowired 
	private JwtService jwtService = new JwtService();

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
	@RequestMapping(value = "/posts", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<String> addPost(@RequestHeader("Authorization") String authHeader, @ModelAttribute Posts data,
			@RequestParam("files") MultipartFile[] files) {
		
		String username = jwtService.getUsernameFromToken(authHeader);
		HttpStatus httpStatus = null;
		String result = "";

		// Add medical
		try {
			if (postsService.addPost(username, data, files) != null) {
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
	
	// Update post
	@RequestMapping(value = "/posts", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<Posts> updatePost(@RequestHeader("Authorization") String authHeader,  @RequestBody Posts data) {
		
		//String aaString = JwtService.getUsernameFromToken(authHeader);
		String username = jwtService.getUsernameFromToken(authHeader);
		
		HttpStatus httpStatus = null;
		Posts posts = new Posts();
		posts = postsService.updatePost(username, data);
		// Add medical
		try {
				try {
					if (posts != null) {
						httpStatus = HttpStatus.OK;
					}else {
						httpStatus = HttpStatus.BAD_REQUEST;
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					httpStatus = HttpStatus.BAD_REQUEST;
					System.out.println("Post controller" + e);
				}
				
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Post controller" + e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Posts>(posts, httpStatus);
	}
	
	// Delete post
		@RequestMapping(value = "/posts/{id_post}", method = RequestMethod.DELETE, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<String> deletePost(@RequestHeader("Authorization") String authHeader,
				@PathVariable("id_post") Integer id_post) {
			HttpStatus httpStatus = null;
			String result = "";
			String username = jwtService.getUsernameFromToken(authHeader);
			try {
				if (postsService.deletePost(username, id_post)) {
					result = "Delete post success";
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
					result = "Delete post fail";
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			return new ResponseEntity<String>(result, httpStatus);
		}
		
	// Get comment post
		@RequestMapping(value = "/comment/{id_post}", method = RequestMethod.GET, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<List<Posts_Cmt>> getCommentPost(
				@PathVariable("id_post") Integer id_post) {
			HttpStatus httpStatus = null;
			List<Posts_Cmt> list = null;
			try {
				if (postsService.getCommentPost(id_post) != null) {
					list = postsService.getCommentPost(id_post);
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			return new ResponseEntity<List<Posts_Cmt>>(list, httpStatus);
		}
		
	// Add comment post
		@RequestMapping(value = "/comment/{id_post}", method = RequestMethod.POST, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<String> addCommentPost(@RequestHeader("Authorization") String authHeader,
				@PathVariable("id_post") Integer id_post, @RequestBody Posts_Cmt posts_Cmt) {
			HttpStatus httpStatus = null;
			String result = "";
			String username = jwtService.getUsernameFromToken(authHeader);
			try {
				if (postsService.addCommentPost(username, id_post, posts_Cmt)) {
					result = "Add comment success";
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
					result = "Add comment fail";
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			return new ResponseEntity<String>(result, httpStatus);
		}
}
