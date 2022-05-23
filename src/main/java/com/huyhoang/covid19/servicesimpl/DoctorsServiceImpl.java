package com.huyhoang.covid19.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.dao.DoctorsDAO;
import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.Doctors;
import com.huyhoang.covid19.services.DoctorsService;

@Service
@Transactional
public class DoctorsServiceImpl implements DoctorsService{
	
	@Autowired
	private DoctorsDAO doctorsDAO;
	
	public List<Doctors> getDoctors(){
		return doctorsDAO.getDoctors();
	}
	
	public Doctors addDoctors(Doctors data, Departments departments ,MultipartFile[] files) {
		return doctorsDAO.addDoctors(data,departments, files);
	}
	
	public Doctors updateDoctors(Doctors data, Departments departments ,MultipartFile[] files) {
		return doctorsDAO.updateDoctors(data,departments, files);
	}
	
	public boolean deleteDoctor(int id_doctor) {
		return doctorsDAO.deleteDoctor(id_doctor);
	}
	
	public List<Doctors> getDoctorsByDepartment(int id_depart){
		return doctorsDAO.getDoctorsByDepartment(id_depart);
	}
}
