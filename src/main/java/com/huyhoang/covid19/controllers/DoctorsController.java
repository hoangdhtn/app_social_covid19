package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.AddDoctorData;
import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.Doctors;
import com.huyhoang.covid19.entities.Notification;
import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.services.DoctorsService;
import com.huyhoang.covid19.services.JwtService;

@RestController
@RequestMapping("/api")
public class DoctorsController {

	@Autowired
	private DoctorsService doctorsService;

	@Autowired
	private JwtService jwtService = new JwtService();

	// Get doctors
	@RequestMapping(value = "/doctors", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Doctors>> getDoctors() {
		HttpStatus httpStatus = null;

		List<Doctors> list = null;
		try {
			list = doctorsService.getDoctors();
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
		return new ResponseEntity<List<Doctors>>(list, httpStatus);
	}
	
	// Get doctors by department
		@RequestMapping(value = "/doctors/{id_depart}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<List<Doctors>> getDoctorsbyDepartment(@PathVariable("id_depart") int id_depart) {
			HttpStatus httpStatus = null;

			List<Doctors> list = null;
			try {
				list = doctorsService.getDoctorsByDepartment(id_depart);
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
			return new ResponseEntity<List<Doctors>>(list, httpStatus);
		}

	// Add doctor, sử dụng form data bởi vì cần upload file img
	@RequestMapping(value = "/doctors", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<Doctors> addDoctor(@RequestPart("data") AddDoctorData data,
			@RequestParam(name = "files", required = false) MultipartFile[] files) {
		HttpStatus httpStatus = null;
		Doctors doctors = null;
		System.out.print(data);
		try {
			doctors = doctorsService.addDoctors(data.getDoctor(), data.getDepartments(), files);
			if (doctors != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.print("Loi doctor " + e.toString());
		}

		return new ResponseEntity<Doctors>(doctors, httpStatus);
	}

	// Update doctor, sử dụng form data bởi vì cần upload file img
	@RequestMapping(value = "/doctors", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<Doctors> updateDoctor(@RequestPart("data") AddDoctorData data,
			@RequestPart(name = "files", required = false) MultipartFile[] files) {
		HttpStatus httpStatus = null;
		Doctors doctors = null;
		System.out.print(files);
		try {
			doctors = doctorsService.updateDoctors(data.getDoctor(), data.getDepartments(), files);
			if (doctors != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.print("Loi doctor " + e.toString());
		}

		return new ResponseEntity<Doctors>(doctors, httpStatus);
	}

	// Delete doctor, sử dụng form data bởi vì cần upload file img
	@RequestMapping(value = "/doctors/{id_doctor}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<Notification> deleteDoctor(@PathVariable("id_doctor") int id_doctor) {
		HttpStatus httpStatus = null;
		Notification notification = new Notification();
		try {
			if (doctorsService.deleteDoctor(id_doctor)) {
				httpStatus = HttpStatus.OK;
				notification.setStatus("success");
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				notification.setStatus("fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.print("Loi doctor " + e.toString());
		}

		return new ResponseEntity<Notification>(notification, httpStatus);
	}
}
