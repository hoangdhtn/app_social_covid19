package com.huyhoang.covid19.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.services.JwtService;
import com.huyhoang.covid19.services.LikesService;

@RestController
@RequestMapping("/api")
public class LikesController {

	@Autowired
	private LikesService likesService;
	
	@Autowired 
	private JwtService jwtService = new JwtService();
	
	// Like post
	@RequestMapping(value = "/like/{id_post}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> likePost(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_post") Integer id_post) {
		HttpStatus httpStatus = null;
		String result = "";
		String username = jwtService.getUsernameFromToken(authHeader);
		try {
			if (likesService.likePost(username, id_post)) {
				result = "Like post success";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Like post fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println("Loiiii" + e);
		}

		return new ResponseEntity<String>(result, httpStatus);
	}
	
	// Unlike post
	@RequestMapping(value = "/like/{id_post}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<String> unlikePost(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_post") Integer id_post) {
		HttpStatus httpStatus = null;
		String result = "";
		String username = jwtService.getUsernameFromToken(authHeader);
		try {
			if (likesService.unlikePost(username, id_post)) {
				result = "Unlike post success";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Unlike post fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println("Loiiii" + e);
		}

		return new ResponseEntity<String>(result, httpStatus);
	}
}
