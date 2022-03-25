package com.huyhoang.covid19.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.MedicalInfo;
import com.huyhoang.covid19.entities.Users;


@Repository
public class MedicalInfoDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public List<MedicalInfo> getMedicalInfo(Users user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from MedicalInfo where id_user = :id";
		Query query = session.createQuery(hql, MedicalInfo.class);
		query.setParameter("id", user.getId());
		
		List<MedicalInfo> listMedical = query.list();
		
		if (listMedical != null && listMedical.size() > 0) {
			return listMedical;
		}else {
			return null;
		}
		
	}
}
