package com.huyhoang.covid19.controllers;

import java.util.List;

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

import com.huyhoang.covid19.entities.Likes;
import com.huyhoang.covid19.entities.Notification;
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
	public ResponseEntity<Notification> likePost(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_post") Integer id_post) {
		HttpStatus httpStatus = null;
		Notification notification = new Notification();
		String username = jwtService.getUsernameFromToken(authHeader);
		try {
			if (likesService.likePost(username, id_post)) {
				notification.setStatus("success");
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				notification.setStatus("fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			notification.setStatus("fail");
			System.out.println("Loiiii" + e);
		}

		return new ResponseEntity<Notification>(notification, httpStatus);
	}
	
	// Unlike post
	@RequestMapping(value = "/like/{id_post}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<Notification> unlikePost(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_post") Integer id_post) {
		HttpStatus httpStatus = null;
		Notification notification = new Notification();
		String username = jwtService.getUsernameFromToken(authHeader);
		try {
			if (likesService.unlikePost(username, id_post)) {
				notification.setStatus("success");
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				notification.setStatus("fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println("Loiiii" + e);
			notification.setStatus("fail");
		}

		return new ResponseEntity<Notification>(notification, httpStatus);
	}
	
	// Get Likes
	@RequestMapping(value = "/getlike/{id_post}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<List<Likes>> countlikePost(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_post") Integer id_post) {
		HttpStatus httpStatus = null;
		String username = jwtService.getUsernameFromToken(authHeader);
		List<Likes> list = null;
		try {
			list = likesService.getLikePost(username, id_post);
			System.out.println("LIKE " + list);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			// TODO: handle exception
			list = null;
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println("Loiiii" + e);
		}

		return new ResponseEntity<List<Likes>>(list, httpStatus);
	}
}
