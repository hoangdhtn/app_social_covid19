package com.huyhoang.covid19.entities;

import org.springframework.web.multipart.MultipartFile;

public class AddDoctorData {
	private Doctors doctor;
	private MultipartFile[] files;
	private Departments departments;
	
	public AddDoctorData() {
		
	}
	
	public AddDoctorData(Doctors doctor, MultipartFile[] files, Departments departments) {
		this.doctor = doctor;
		this.files = files;
		this.departments = departments;
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}
	
	
}
