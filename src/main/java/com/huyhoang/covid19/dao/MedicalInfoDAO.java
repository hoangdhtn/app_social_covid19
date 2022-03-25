package com.huyhoang.covid19.dao;

import java.util.Date;
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
	public List<MedicalInfo> getMedicalInfos(Users user) {
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
	
	public MedicalInfo getDetailMedicalInfo(Integer id_medicalinfo) {
		Session session = sessionFactory.getCurrentSession();
		
		MedicalInfo medicalInfo = session.get(MedicalInfo.class, id_medicalinfo);
		
		if(medicalInfo != null) {
			return medicalInfo;
		}else {
			return null;
		}
	}
	
	public MedicalInfo addMedicalInfo(Integer id_user, MedicalInfo data) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		MedicalInfo medicalInfo = new MedicalInfo();
		medicalInfo.setId_user(id_user);
		medicalInfo.setName(data.getName());
		medicalInfo.setInfo(data.getInfo());
		medicalInfo.setEnabled(true);
		medicalInfo.setCreated_at(date);
		medicalInfo.setUpdated_at(date);
		session.save(medicalInfo);
		
		return medicalInfo;
		
	}
	
	public MedicalInfo updateMedicalInfo(Integer id_user, MedicalInfo data) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		MedicalInfo medicalInfo = session.get(MedicalInfo.class, data.getId());
		medicalInfo.setId_user(id_user);
		medicalInfo.setName(data.getName());
		medicalInfo.setInfo(data.getInfo());
		medicalInfo.setEnabled(true);
		medicalInfo.setCreated_at(date);
		medicalInfo.setUpdated_at(date);
		session.save(medicalInfo);
		
		return medicalInfo;
	}
	
	public Boolean deleteMedicalInfo(Integer id_user, MedicalInfo data) {
		Session session = sessionFactory.getCurrentSession();
		MedicalInfo medicalInfo = session.get(MedicalInfo.class, data.getId());
		if (medicalInfo != null && id_user == data.getId_user()) {
			session.delete(medicalInfo);
			return true;
		}else {
			return false;
		}
	}
	
}
