package com.huyhoang.covid19.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huyhoang.covid19.entities.ResetPassword;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.AuthService;
import com.huyhoang.covid19.services.JwtService;
import com.huyhoang.covid19.services.UsersService;

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

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthService authService;

	// List all User
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<Users> getUsers() {
		List<Users> list = usersService.getAllUsers();
		return list;
	}

	// Get details user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Users getUsers(@PathVariable("id") Integer id) {
		return usersService.getUsers(id);
	}

	// Update user
	@RequestMapping(value = "/users", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<Users> updateUser(@RequestHeader("Authorization") String authHeader,
			@ModelAttribute Users data, @RequestParam("files") MultipartFile[] files) {
		String username = jwtService.getUsernameFromToken(authHeader);
		HttpStatus httpStatus = null;

		Users user = new Users();
		user = data;
		user = usersService.updateUsers(username, data, files);

		try {
			try {
				if (user != null) {
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}

			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.BAD_REQUEST;
				System.out.println("upy controller" + e);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Post controller" + e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Users>(user, httpStatus);
	}

	// Add user
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Users addUsers(@RequestBody Users form) {
		return usersService.addUsers(form);
	}

	// Delete user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> deleteUsers(@PathVariable("id") Integer id) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if (usersService.deleteUser(id)) {
				result = "Delete success";
				httpStatus = HttpStatus.OK;
			} else {
				result = "Delete fail";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	// User Follow
	@RequestMapping(value = "/follow/{id}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> userFollow(@RequestBody Users users, @PathVariable("id") Integer id_follow) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if (usersService.userFollow(users, id_follow)) {
				result = "Follow success";
				httpStatus = HttpStatus.OK;
			} else {
				result = "Follow fail";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	// User UnFollow
	@RequestMapping(value = "/follow/{id}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> userUnFollow(@RequestBody Users users, @PathVariable("id") Integer id_follow) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if (usersService.userUnFollow(users, id_follow)) {
				result = "UnFollow success";
				httpStatus = HttpStatus.OK;
			} else {
				result = "UnFollow fail";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	// Login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody Users user) {
		String result = "";
		HttpStatus httpStatus = null;
		// System.out.print(user.getUsername() + user.getPassword());
		try {
			if (authService.checkLogin(user)) {
				result = jwtService.generateTokenLogin(user.getUsername());
				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong userId and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			// System.out.print(ex);
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	// Signup
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> signup(HttpServletRequest request, @RequestBody Users user) {
		String result = "";
		HttpStatus httpStatus = null;

		System.out.print(user.getUsername());
		// System.out.print(user.getUsername() + user.getPassword());
		try {
			if (authService.signupUser(user) != null) {
				authService.signupUser(user);
				httpStatus = HttpStatus.OK;
				result = "Sign up success";
			} else {
				result = "Sign up fail";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			// System.out.print(ex);
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	// Reset password
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> resetPassword(@RequestBody ResetPassword formData) {
		String result = "";
		HttpStatus httpStatus = null;

		try {
			if (authService.resetPassword(formData)) {
				result = "Change password success";
				httpStatus = HttpStatus.OK;
			} else {
				result = "Change password fail";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}
}
