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
	public ResponseEntity<List<MedicalInfo>> getAllMedical(@RequestBody Users user) {

		HttpStatus httpStatus = null;

		List<MedicalInfo> list = medicalInfoService.getMedicalInfos(user);
		try {
			if (list != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<MedicalInfo>>(list, httpStatus);
	}

	// Get detail medical
	@RequestMapping(value = "/medical/{id_user}/{id_medical}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<MedicalInfo> addMedicalInfo(@PathVariable("id_user") Integer id_user,
			@PathVariable("id_medical") Integer id_medical) {
		HttpStatus httpStatus = null;
		MedicalInfo medicalInfo = medicalInfoService.getDetailMedicalInfo(id_medical);
		MedicalInfo result = new MedicalInfo();
		try {
			if (medicalInfo != null && medicalInfo.getId_user() == id_user) {
				result = medicalInfoService.getDetailMedicalInfo(id_medical);
				// System.out.println(result);
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<MedicalInfo>(result, httpStatus);
	}

	// Add medical
	@RequestMapping(value = "/medical/{id_user}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> addMedicalInfo(@PathVariable("id_user") Integer id_user,
			@RequestBody MedicalInfo data) {
		HttpStatus httpStatus = null;
		String result = "";
		try {
			if (medicalInfoService.addMedicalInfo(id_user, data) != null) {
				result = "Add medical success";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Add medical fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}

	// Update medical
	@RequestMapping(value = "/medical/{id_user}", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> updateMedicalInfo(@PathVariable("id_user") Integer id_user,
			@RequestBody MedicalInfo data) {
		HttpStatus httpStatus = null;
		String result = "";
		try {
			if (medicalInfoService.updateMedicalInfo(id_user, data) != null && id_user == data.getId_user()) {
				result = "Update medical success";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Update medical fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, httpStatus);
	}
	
	// Delete medical
		@RequestMapping(value = "/medical/{id_user}", method = RequestMethod.DELETE, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<String> deleteMedicalInfo(@PathVariable("id_user") Integer id_user,
				@RequestBody MedicalInfo data) {
			HttpStatus httpStatus = null;
			String result = "";
			try {
				if (medicalInfoService.deleteMedicalInfo(id_user, data) && id_user == data.getId_user()) {
					result = "Delete medical success";
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
					result = "Delete medical fail";
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			return new ResponseEntity<String>(result, httpStatus);
		}
}
