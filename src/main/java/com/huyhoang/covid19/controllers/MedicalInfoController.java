package com.huyhoang.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.MedicalInfo;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.MedicalInfoService;

@RestController
@RequestMapping("/api")
public class MedicalInfoController {

	@Autowired
	private MedicalInfoService medicalInfoService;
	
	// List all medical
		@RequestMapping(value = "/medical", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<List<MedicalInfo>> getAllMedical(@RequestBody Users user){
			HttpStatus httpStatus = null;
			List<MedicalInfo> list = medicalInfoService.getMedicalInfo(user);
			try {
				if(list != null) {
					httpStatus = HttpStatus.OK;
				}else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return new ResponseEntity<List<MedicalInfo>>(list, httpStatus);
		}
	
}
