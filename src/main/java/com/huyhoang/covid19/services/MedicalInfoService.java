package com.huyhoang.covid19.services;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;


import com.huyhoang.covid19.entities.MedicalInfo;



public interface MedicalInfoService {
	
	public List<MedicalInfo> getMedicalInfos(String username, int position, int pageSize);
	
	public MedicalInfo getDetailMedicalInfo(String username,Integer id_medicalinfo);
	
	public MedicalInfo addMedicalInfo(String username, MedicalInfo data, MultipartFile[] files);
	
	public MedicalInfo updateMedicalInfo(String username, MedicalInfo data);
	
	public Boolean deleteMedicalInfo(String username, Integer id_medical);
}
