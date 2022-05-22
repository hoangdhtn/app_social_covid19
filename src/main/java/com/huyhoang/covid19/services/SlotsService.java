package com.huyhoang.covid19.services;

import java.time.LocalTime;
import java.util.List;

import com.huyhoang.covid19.entities.Slots;

public interface SlotsService {
	
	
	public List<Slots> getAllSlot();
	
	public List<Slots> getSlotBusyByDoctor(int id_doctor);
	
	public List<LocalTime> getSlotByDoctor(int id_doctor, String date);
	
	public Slots bookingSlot(Slots data);
	
	public boolean deleteBookingSlot(int id_slot);
}
