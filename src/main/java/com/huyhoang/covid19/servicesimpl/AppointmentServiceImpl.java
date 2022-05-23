package com.huyhoang.covid19.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huyhoang.covid19.dao.AppointmentOrderDAO;
import com.huyhoang.covid19.entities.AppointmentOrder;
import com.huyhoang.covid19.services.AppoitmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppoitmentService {

	@Autowired
	private AppointmentOrderDAO appointmentOrderDAO;
	
	public List<AppointmentOrder> getMyListAppointmentOrders(String username){
		return appointmentOrderDAO.getMyListAppointmentOrders(username);
	}
	
	public AppointmentOrder addAdAppointmentOrder(AppointmentOrder data, String username) {
		return appointmentOrderDAO.addAdAppointmentOrder(data, username);
	}
}
