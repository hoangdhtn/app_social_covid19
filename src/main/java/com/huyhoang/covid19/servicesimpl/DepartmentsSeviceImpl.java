package com.huyhoang.covid19.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.huyhoang.covid19.dao.DepartmentsDAO;
import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.services.DepartmentsSevice;

@Service
@Transactional
public class DepartmentsSeviceImpl implements DepartmentsSevice{

	@Autowired
	private DepartmentsDAO departmentsDAO;
	
	public List<Departments> getDepartments(){
		return departmentsDAO.getDepartments();
	}
	
	public Departments addDepartment(Departments data) {
		return departmentsDAO.addDepartment(data);
	}
	
	public Departments updateDepartment(Departments data) {
		return departmentsDAO.updateDepartment(data);
	}
	
	public boolean deleteDepartment(Departments data) {
		return departmentsDAO.deleteDepartment(data);
	}
}
