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
	
	public List<MedicalInfo> getMedicalInfos(String username){
		return medicalInfoDAO.getMedicalInfos(username);
	}
	
	public MedicalInfo getDetailMedicalInfo(Integer id_medicalinfo) {
		return medicalInfoDAO.getDetailMedicalInfo(id_medicalinfo);
	}
	
	public MedicalInfo addMedicalInfo(String username, MedicalInfo data, MultipartFile[] files) {
		return medicalInfoDAO.addMedicalInfo(username, data, files);
	}
	
	public MedicalInfo updateMedicalInfo(String username, MedicalInfo data) {
		return medicalInfoDAO.updateMedicalInfo(username, data);
	}
	
	public Boolean deleteMedicalInfo(String username, Integer id_medical) {
		return medicalInfoDAO.deleteMedicalInfo(username, id_medical);
	}
}
