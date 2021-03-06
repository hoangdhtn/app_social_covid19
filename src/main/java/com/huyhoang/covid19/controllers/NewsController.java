package com.huyhoang.covid19.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.AddNewsData;
import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News;
import com.huyhoang.covid19.services.JwtService;
import com.huyhoang.covid19.services.NewsService;

@RestController
@RequestMapping("/api")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private JwtService jwtService = new JwtService();

	// Get all news
	@RequestMapping(value = "/news/{position}/{pagesize}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<News>> getAllNews(@PathVariable(name = "position") Integer position,
			@PathVariable(name = "pagesize") Integer pagesize) {

		HttpStatus httpStatus = null;

		List<News> list = null;
		try {
			if (newsService.getAllNews(position, pagesize) != null) {
				list = newsService.getAllNews(position, pagesize);
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<News>>(list, httpStatus);
	}
	
	// Get all news by ID Cate
		@RequestMapping(value = "/news/{id_cate}/{position}/{pagesize}", method = RequestMethod.GET, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<List<Object[]>> getAllNews(@PathVariable(name = "id_cate") Integer id_cate, @PathVariable(name = "position") Integer position,
				@PathVariable(name = "pagesize") Integer pagesize) {

			HttpStatus httpStatus = null;

			List<Object[]> list = null;
			try {
				if (newsService.getNewsByCate(id_cate,position, pagesize) != null) {
					list = newsService.getNewsByCate(id_cate,position, pagesize);
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return new ResponseEntity<List<Object[]>>(list, httpStatus);
		}

	// Add news
	@SuppressWarnings("unused")
	@RequestMapping(value = "/news", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<News> addNews(@RequestHeader("Authorization") String authHeader,
			@RequestPart("data")  AddNewsData data, @RequestParam MultipartFile[] files) {
		String username = jwtService.getUsernameFromToken(authHeader);
		HttpStatus httpStatus = null;
		News news = new News();
		Set<Category> cate = (Set<Category>) data.getCategories();
		System.out.println("NEWS CC" + data.getNews());
		try {
			if (data != null) {
				news = newsService.addNews(username, data.getNews(), cate, files);
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
			return null;
		}

		return new ResponseEntity<News>(news, httpStatus);
	}

	// Update news
	@SuppressWarnings("unused")
	@RequestMapping(value = "/news", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<News> updateNews(@RequestHeader("Authorization") String authHeader,
			@RequestBody AddNewsData data) {
		String username = jwtService.getUsernameFromToken(authHeader);
		HttpStatus httpStatus = null;
		News news = new News();
		Set<Category> cate = (Set<Category>) data.getCategories();
		try {
			if (data != null) {
				news = newsService.updateNews(username, data);
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
			return null;
		}

		return new ResponseEntity<News>(news, httpStatus);
	}

	// Delete news
	@RequestMapping(value = "/news/{id_news}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> deleteNews(@PathVariable(name = "id_news") Integer id_news) {
		HttpStatus httpStatus = null;
		String result = "";
		try {
			if (newsService.deleteNews(id_news)) {
				result = "Delete news success";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Delete news fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}
}
