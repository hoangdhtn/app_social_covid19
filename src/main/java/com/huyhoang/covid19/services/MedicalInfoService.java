package com.huyhoang.covid19.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huyhoang.covid19.dao.MedicalInfoDAO;
import com.huyhoang.covid19.entities.MedicalInfo;
import com.huyhoang.covid19.entities.Users;

@Service
@Transactional
public class MedicalInfoService {

	@Autowired
	private MedicalInfoDAO medicalInfoDAO;
	
	public List<MedicalInfo> getMedicalInfos(Users user){
		return medicalInfoDAO.getMedicalInfos(user);
	}
	
	public MedicalInfo getDetailMedicalInfo(Integer id_medicalinfo) {
		return medicalInfoDAO.getDetailMedicalInfo(id_medicalinfo);
	}
}
