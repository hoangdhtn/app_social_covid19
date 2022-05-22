package com.huyhoang.covid19.services;

import java.util.List;

import com.huyhoang.covid19.entities.Departments;

public interface DepartmentsSevice {
	public List<Departments> getDepartments();
	
	public Departments addDepartment(Departments data);
	
	public Departments updateDepartment(Departments data);
	
	public boolean deleteDepartment(Departments data);
}
