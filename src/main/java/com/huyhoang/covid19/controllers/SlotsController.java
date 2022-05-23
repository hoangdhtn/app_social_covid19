package com.huyhoang.covid19.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.Doctors;
import com.huyhoang.covid19.entities.ListTimeAvailable;
import com.huyhoang.covid19.entities.Notification;
import com.huyhoang.covid19.entities.Slots;
import com.huyhoang.covid19.entities.TimeAvailable;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.services.JwtService;
import com.huyhoang.covid19.services.SlotsService;

@RestController
@RequestMapping("/api")
public class SlotsController {

	@Autowired
	private SlotsService slotsService;

	@Autowired
	private JwtService jwtService = new JwtService();

	// Get All Slot
	@RequestMapping(value = "/slots", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Slots>> getAllSlot(SecurityContextHolderAwareRequestWrapper request) {
		HttpStatus httpStatus = null;
		List<Slots> list = null;
		if (request.isUserInRole("ROLE_DOCTOR") || request.isUserInRole("ROLE_ADMIN")) {
			try {
				list = slotsService.getAllSlot();
				if (list != null) {
					httpStatus = HttpStatus.OK;
				} else {
					list = null;
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.print("Loi slot " + e.toString());
			}
		} else {
			httpStatus = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<List<Slots>>(list, httpStatus);

	}

	// Get All Slot busy by doctor
	@RequestMapping(value = "/slots/{id_doctor}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Slots>> getAllSlot(SecurityContextHolderAwareRequestWrapper request,
			@PathVariable("id_doctor") int id_doctor) {
		HttpStatus httpStatus = null;
		List<Slots> list = null;
		if (request.isUserInRole("ROLE_DOCTOR") || request.isUserInRole("ROLE_ADMIN")) {
			try {
				list = slotsService.getSlotBusyByDoctor(id_doctor);
				if (list != null) {
					httpStatus = HttpStatus.OK;
				} else {
					list = null;
					httpStatus = HttpStatus.BAD_REQUEST;
				}
			} catch (Exception e) {
				// TODO: handle exception
				// TODO: handle exception
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.print("Loi slot " + e.toString());
			}
		} else {
			httpStatus = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<List<Slots>>(list, httpStatus);

	}

	// Get All Slot by doctor
	@RequestMapping(value = "/slots/available/{id_doctor}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<ArrayList<TimeAvailable>> getSlotByDoctor(SecurityContextHolderAwareRequestWrapper request,
			@PathVariable("id_doctor") int id_doctor, @RequestParam("date") String date) {

		HttpStatus httpStatus = null;
		ArrayList<TimeAvailable> list = null;

		try {
			list = slotsService.getSlotByDoctor(id_doctor, date);
			if (list != null) {
				httpStatus = HttpStatus.OK;
			} else {
				list = null;
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			// TODO: handle exception
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.print("Loi slot " + e.toString());
		}
		return new ResponseEntity<ArrayList<TimeAvailable>>(list, httpStatus);

	}

	// Get booking Slot
	@RequestMapping(value = "/slots", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Slots> bookingSlot(@RequestBody Slots data) {
		HttpStatus httpStatus = null;
		Slots slots = null;
		try {
			slots = slotsService.bookingSlot(data);
			if(slots != null) {
				httpStatus = HttpStatus.OK;
			} else {
				slots = null;
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Loi booking " + e.toString());
		}
		return new ResponseEntity<Slots>(slots, httpStatus);
	}
	
	// Delete booking Slot
		@RequestMapping(value = "/slots/{id_slot}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
		@ResponseBody
		public ResponseEntity<Notification> deletebookingSlot(@PathVariable("id_slot") int id_slot) {
			HttpStatus httpStatus = null;
			Notification notification = new Notification();
			try {
				if(slotsService.deleteBookingSlot(id_slot)) {
					httpStatus = HttpStatus.OK;
					notification.setStatus("success");
				}else {
					httpStatus = HttpStatus.BAD_REQUEST;
					notification.setStatus("fail");
				}
			} catch (Exception e) {
				// TODO: handle exception
				httpStatus = HttpStatus.BAD_REQUEST;
				notification.setStatus("fail");
			}
			return new ResponseEntity<Notification>(notification, httpStatus);
		}
}
