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

import com.huyhoang.covid19.dao.AuthDAO;
import com.huyhoang.covid19.entities.MedicalInfo;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.JwtService;
import com.huyhoang.covid19.services.MedicalInfoService;

@RestController
@RequestMapping("/api")
public class MedicalInfoController {

	@Autowired
	private MedicalInfoService medicalInfoService;
	
	@Autowired 
	private JwtService jwtService = new JwtService();
	
	@Autowired
	private AuthDAO authDAO;

	// List all medical
	@RequestMapping(value = "/medical/{position}/{pagesize}", method = RequestMethod.GET, 
			produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<MedicalInfo>> getAllMedical(@RequestHeader("Authorization") String authHeader, @PathVariable(name = "position") Integer position,
			@PathVariable(name = "pagesize") Integer pagesize) {
		String username = jwtService.getUsernameFromToken(authHeader);
		HttpStatus httpStatus = null;

		List<MedicalInfo> list = medicalInfoService.getMedicalInfos(username, position, pagesize);
		try {
			if (list != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.print("Loi Ne " + e.toString());
		}
		return new ResponseEntity<List<MedicalInfo>>(list, httpStatus);
	}

	// Get detail medical
	@RequestMapping(value = "/medical/{id_medical}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<MedicalInfo> addMedicalInfo(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_medical") Integer id_medical) {
		String username = jwtService.getUsernameFromToken(authHeader);
		
		HttpStatus httpStatus = null;
		MedicalInfo medicalInfo = medicalInfoService.getDetailMedicalInfo(username, id_medical);
		
		MedicalInfo result = new MedicalInfo();
		try {
			if (medicalInfo != null) {
				result = medicalInfoService.getDetailMedicalInfo(username, id_medical);
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

	// Add medical, sử dụng form data bởi vì cần upload file img
	@RequestMapping(value = "/medical", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<String> addMedicalInfo(@RequestHeader("Authorization") String authHeader,
			@ModelAttribute MedicalInfo data, @RequestParam("files") MultipartFile[] files) {
		HttpStatus httpStatus = null;
		String result = "";
		String username = jwtService.getUsernameFromToken(authHeader);
		// Add medical
		try {
			if (medicalInfoService.addMedicalInfo(username, data, files) != null) {
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
	@RequestMapping(value = "/medical", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<MedicalInfo> updateMedicalInfo(@RequestHeader("Authorization") String authHeader,
			@RequestBody MedicalInfo data) {
		HttpStatus httpStatus = null;
		MedicalInfo medicalInfo = new MedicalInfo();
		String username = jwtService.getUsernameFromToken(authHeader);
		
		medicalInfo = medicalInfoService.updateMedicalInfo(username, data);
		try {
			if (medicalInfo != null) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<MedicalInfo>(medicalInfo, httpStatus);
	}

	// Delete medical
	@RequestMapping(value = "/medical/{id_medical}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<String> deleteMedicalInfo(@RequestHeader("Authorization") String authHeader,
			@PathVariable("id_medical") Integer id_medical) {
		HttpStatus httpStatus = null;
		String result = "";
		String username = jwtService.getUsernameFromToken(authHeader);
		try {
			if (medicalInfoService.deleteMedicalInfo(username, id_medical)) {
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
