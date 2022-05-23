package com.huyhoang.covid19.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.AppointmentOrder;
import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.services.AppoitmentService;
import com.huyhoang.covid19.services.JwtService;

@RestController
@RequestMapping("/api")
public class AppointmentController {
	
	@Autowired
	private AppoitmentService appoitmentService;
	
	@Autowired
	private JwtService jwtService = new JwtService();
	
	// Get My Appointment 
		@RequestMapping(value = "/appointment", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<List<AppointmentOrder>> getMyAppointment(SecurityContextHolderAwareRequestWrapper request){
			
			HttpStatus httpStatus = null;
			String username = request.getUserPrincipal().getName();
			List<AppointmentOrder> list = null;
			
			try {
				list = appoitmentService.getMyListAppointmentOrders(username);
				if (list != null) {
					httpStatus = HttpStatus.OK;
				} else {
					list = null;
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.print("Loi appointment " + e.toString());
			}
			
			return new ResponseEntity<List<AppointmentOrder>>(list, httpStatus);
		}
}
