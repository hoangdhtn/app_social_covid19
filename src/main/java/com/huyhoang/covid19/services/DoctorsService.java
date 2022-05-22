package com.huyhoang.covid19.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.Doctors;

public interface DoctorsService {
	
	public List<Doctors> getDoctors();
	
	public Doctors addDoctors(Doctors data, Departments departments ,MultipartFile[] files);
	
	public Doctors updateDoctors(Doctors data, Departments departments ,MultipartFile[] files);
	
	public boolean deleteDoctor(int id_doctor);
}
