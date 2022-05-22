package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.MedicalInfo;
import com.huyhoang.covid19.entities.Notification;
import com.huyhoang.covid19.services.DepartmentsSevice;
import com.huyhoang.covid19.services.JwtService;

@RestController
@RequestMapping("/api")
public class DepartmentsController {

	@Autowired
	private DepartmentsSevice departmentsSevice;

	@Autowired
	private JwtService jwtService = new JwtService();

	// Get department
	@RequestMapping(value = "/departments", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Departments>> getDepartments() {
		HttpStatus httpStatus = null;

		List<Departments> list = null;
		try {
			list = departmentsSevice.getDepartments();
			if (list != null) {
				httpStatus = HttpStatus.OK;
			} else {
				list = null;
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.print("Loi department " + e.toString());
		}
		return new ResponseEntity<List<Departments>>(list, httpStatus);
	}

	// Add department
	@RequestMapping(value = "/departments", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Departments> addDepartment(@RequestBody Departments data) {
		HttpStatus httpStatus = null;
		Departments departments = new Departments();
		try {
			departments = departmentsSevice.addDepartment(data);
			if (departments != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}

		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Departments>(departments, httpStatus);
	}

	// Update department
	@RequestMapping(value = "/departments", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Departments> updateDepartment(@RequestBody Departments data) {
		HttpStatus httpStatus = null;
		Departments departments = new Departments();
		try {
			departments = departmentsSevice.updateDepartment(data);
			if (departments != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}

		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Departments>(departments, httpStatus);
	}
	
	// Delete department
		@RequestMapping(value = "/departments", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<Notification> deleteDepartment(@RequestBody Departments data) {
			HttpStatus httpStatus = null;
			Notification notification = new Notification();
			try {
				departmentsSevice.updateDepartment(data);
				if (departmentsSevice.deleteDepartment(data)) {
					httpStatus = HttpStatus.OK;
					notification.setStatus("success");
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
					notification.setStatus("fail");
				}

			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				notification.setStatus("fail");
			}
			return new ResponseEntity<Notification>(notification, httpStatus);
		}

}
