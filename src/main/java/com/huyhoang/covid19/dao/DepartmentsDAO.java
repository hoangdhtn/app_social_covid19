package com.huyhoang.covid19.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Departments;

@Repository
public class DepartmentsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Departments> getDepartments() {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Departments";
		Query query = session.createQuery(hql, Departments.class);

		List<Departments> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}
	
	public Departments addDepartment(Departments data) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Departments departments = new Departments();
			departments.setName(data.getName());
			session.save(departments);
			return departments;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Departments updateDepartment(Departments data) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Departments departments = session.find(Departments.class, data.getId());
			departments.setName(data.getName());
			session.update(departments);
			return departments;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public boolean deleteDepartment(Departments data) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Departments departments = session.find(Departments.class, data.getId());
			session.delete(departments);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
