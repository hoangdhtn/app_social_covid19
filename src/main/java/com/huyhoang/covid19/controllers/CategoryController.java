package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// Get all category
	@RequestMapping(value = "/category", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Category>> getAllPosts() {

		HttpStatus httpStatus = null;

		List<Category> list = null;
		try {
			if (categoryService.getAllCategory() != null) {
				list = categoryService.getAllCategory();
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<Category>>(list, httpStatus);
	}

	// Add category
	@RequestMapping(value = "/category", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Category> addCategory(@RequestBody Category data) {
		HttpStatus httpStatus = null;
		Category category = new Category();
		try {
			category = categoryService.addCategory(data);
			if (category != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}

		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Category>(category, httpStatus);
	}

	// Update category
	@RequestMapping(value = "/category", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Category> updateCategory(@RequestBody Category data) {
		HttpStatus httpStatus = null;
		Category category = new Category();
		try {
			category = categoryService.updateCategory(data);
			if (category != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}

		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Category>(category, httpStatus);
	}
	
	// Delete category
		@RequestMapping(value = "/category/{id_category}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<String> deleteCategory(@PathVariable(name = "id_category") Integer id_cate){
			HttpStatus httpStatus = null;
			String result = "";
			
			try {
				if(categoryService.deleteCategory(id_cate)) {
					result = "Delete category success";
					httpStatus = HttpStatus.OK;
				} else {
					result = "Delete category fail";
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return new ResponseEntity<String>(result, httpStatus);
		}
}
