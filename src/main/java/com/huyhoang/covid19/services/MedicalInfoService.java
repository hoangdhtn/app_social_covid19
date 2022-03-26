package com.huyhoang.covid19.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	
	public MedicalInfo addMedicalInfo(Integer id_user, MedicalInfo data, MultipartFile[] files) {
		return medicalInfoDAO.addMedicalInfo(id_user, data, files);
	}
	
	public MedicalInfo updateMedicalInfo(Integer id_user, MedicalInfo data) {
		return medicalInfoDAO.updateMedicalInfo(id_user, data);
	}
	
	public Boolean deleteMedicalInfo(Integer id_user, Integer id_medical) {
		return medicalInfoDAO.deleteMedicalInfo(id_user, id_medical);
	}
}
