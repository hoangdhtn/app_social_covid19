package com.huyhoang.covid19.services;

import java.util.List;

import com.huyhoang.covid19.entities.AppointmentOrder;

public interface AppoitmentService {

	public List<AppointmentOrder> getMyListAppointmentOrders(String username);
}
