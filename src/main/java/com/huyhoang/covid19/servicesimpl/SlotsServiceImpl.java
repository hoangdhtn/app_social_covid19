package com.huyhoang.covid19.servicesimpl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huyhoang.covid19.dao.SlotsDAO;
import com.huyhoang.covid19.entities.ListTimeAvailable;
import com.huyhoang.covid19.entities.Slots;
import com.huyhoang.covid19.services.SlotsService;

@Service
@Transactional
public class SlotsServiceImpl implements SlotsService {

	@Autowired
	private SlotsDAO slotsDAO;


	public List<Slots> getAllSlot(){
		return slotsDAO.getAllSlot();
	}
	
	public List<Slots> getSlotBusyByDoctor(int id_doctor){
		return slotsDAO.getSlotBusyByDoctor(id_doctor);
	}
	
	public ListTimeAvailable getSlotByDoctor(int id_doctor, String date){
		return slotsDAO.getSlotByDoctor(id_doctor,date);
	}
	
	public Slots bookingSlot(Slots data) {
		return slotsDAO.bookingSlot(data);
	}
	
	public boolean deleteBookingSlot(int id_slot) {
		return slotsDAO.deleteBookingSlot(id_slot);
	}
}
