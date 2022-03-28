package com.huyhoang.covid19.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.MedicalInfo;
import com.huyhoang.covid19.entities.MedicalInfo_Img;
import com.huyhoang.covid19.entities.Users;

@Repository
public class MedicalInfoDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private AuthDAO authDAO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MedicalInfo> getMedicalInfos(Users user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from MedicalInfo where id_user = :id";
		Query query = session.createQuery(hql, MedicalInfo.class);
		query.setParameter("id", user.getId());

		List<MedicalInfo> listMedical = query.list();

		if (listMedical != null && listMedical.size() > 0) {
			return listMedical;
		} else {
			return null;
		}

	}

	public MedicalInfo getDetailMedicalInfo(Integer id_medicalinfo) {
		Session session = sessionFactory.getCurrentSession();

		MedicalInfo medicalInfo = session.get(MedicalInfo.class, id_medicalinfo);

		if (medicalInfo != null) {
			return medicalInfo;
		} else {
			return null;
		}
	}

	public MedicalInfo addMedicalInfo(String username, MedicalInfo data, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		MedicalInfo medicalInfo = new MedicalInfo();

		Set<MedicalInfo_Img> medicalInfo_Imgs = new HashSet<>();

		// Upload hình ảnh
		File localfile = new File("");
		String uploadRootPath = localfile.getAbsolutePath() + "\\uploads\\";
		System.out.println("Current working directory : " + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		MultipartFile[] fileDatas = files;

		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String name = fileData.getOriginalFilename();
			System.out.println("Client File Name = " + name);

			if (name != null && name.length() > 0) {
				try {
					// Tạo file tại Server.
					String changeName = LocalTime.now().toString().replaceAll("[^A-Za-z0-9]", "");
					String nameupString = changeName + name;
					File serverFile = new File(
							uploadRootDir.getAbsolutePath() + File.separator + nameupString.toLowerCase());

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					MedicalInfo_Img newImg = new MedicalInfo_Img();
					newImg.setName(nameupString);
					newImg.setMedicalinfo(medicalInfo);
					newImg.setCreated_at(date);
					newImg.setUpdated_at(date);
					medicalInfo_Imgs.add(newImg);
					System.out.println("Write file: " + serverFile);
				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
				}
			}
		}

		try {

			medicalInfo.setId_user(user.getId());
			medicalInfo.setName(data.getName());
			medicalInfo.setInfo(data.getInfo());
			medicalInfo.setEnabled(true);
			medicalInfo.setCreated_at(date);
			medicalInfo.setUpdated_at(date);
			medicalInfo.setListImg(medicalInfo_Imgs);
			session.save(medicalInfo);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi ne : " + e);
		}

		return medicalInfo;

	}

	public MedicalInfo updateMedicalInfo(String username, MedicalInfo data) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		MedicalInfo medicalInfo = session.get(MedicalInfo.class, data.getId());
		medicalInfo.setId_user(user.getId());
		medicalInfo.setName(data.getName());
		medicalInfo.setInfo(data.getInfo());
		medicalInfo.setEnabled(true);
		medicalInfo.setCreated_at(date);
		medicalInfo.setUpdated_at(date);
		session.save(medicalInfo);

		return medicalInfo;
	}

	public Boolean deleteMedicalInfo(String username, Integer id_medical) {
		Session session = sessionFactory.getCurrentSession();
		MedicalInfo medicalInfo = session.get(MedicalInfo.class, id_medical);
		Users user = authDAO.loadUsername(username);
		if (medicalInfo != null && user.getId() == medicalInfo.getId_user()) {
			
			Set<MedicalInfo_Img> medicalInfo_Imgs = medicalInfo.getListImg();
			
			for(MedicalInfo_Img img : medicalInfo_Imgs)
			{
				try {
					Files.deleteIfExists(Paths.get("uploads", img.getName()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			session.delete(medicalInfo);
			return true;
		} else {
			return false;
		}
	}

}
