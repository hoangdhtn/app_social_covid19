package com.huyhoang.covid19.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.Doctors;

@Repository
public class DoctorsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Doctors> getDoctors() {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Doctors";
		Query query = session.createQuery(hql, Doctors.class);

		List<Doctors> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}
	
	public List<Doctors> getDoctorsByDepartment(int id_depart) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "FROM Doctors n WHERE n.departments.id = :id_depart";
		Query query = session.createQuery(hql, Doctors.class);
		query.setParameter("id_depart", id_depart);

		List<Doctors> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	public Doctors addDoctors(Doctors data, Departments departments, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();
		Doctors doctors = new Doctors();
		try {

			MultipartFile[] fileDatas = files;
			if (fileDatas.length > 0) {
				System.out.println("AAA" + data.getAva_url());
				if (data.getAva_url() == null) {
					try {
						Files.deleteIfExists(Paths.get("uploads", data.getAva_url()));
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("AAA" + e);
						e.printStackTrace();
					}

					// Upload hình ảnh
					File localfile = new File("");
					String uploadRootPath = localfile.getAbsolutePath() + "\\uploads\\";
					System.out.println("Current working directory : " + uploadRootPath);

					File uploadRootDir = new File(uploadRootPath);
					// Tạo thư mục gốc upload nếu nó không tồn tại.
					if (!uploadRootDir.exists()) {
						uploadRootDir.mkdirs();
					}

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

								BufferedOutputStream stream = new BufferedOutputStream(
										new FileOutputStream(serverFile));
								stream.write(fileData.getBytes());
								stream.close();

								doctors.setAva_url(nameupString.toString());

								System.out.println("Write file: " + nameupString);
								System.out.println("Write file: " + serverFile);
							} catch (Exception e) {
								System.out.println("Error Write file: " + name);
							}
						}
					}
				}

			} else {
				doctors.setAva_url(null);
			}

			doctors.setName(data.getName());
			doctors.setPhone(data.getPhone());
			doctors.setEmail(data.getEmail());
			doctors.setDescription(data.getDescription());
			doctors.setDuration(data.getDuration());
			doctors.setPrice(data.getPrice());
			doctors.setRating(data.getRating());
			doctors.setEnabled(data.getEnabled());

			Departments dep = departments;
			doctors.setDepartments(dep);

			session.save(doctors);
			return doctors;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi ad doctor : " + e.getMessage());
			return null;
		}
	}

	public Doctors updateDoctors(Doctors data, Departments departments, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();
		Doctors doctors = session.find(Doctors.class, data.getId());

		try {

			MultipartFile[] fileDatas = files;
			System.out.println("AAA" + doctors.getAva_url());
			System.out.println("AAA Lengt" + files.length);
			if (doctors.getAva_url() != null) {
				// Upload hình ảnh
				File localfile = new File("");
				String uploadRootPath = localfile.getAbsolutePath() + "\\uploads\\";
				System.out.println("Current working directory : " + uploadRootPath);

				File uploadRootDir = new File(uploadRootPath);
				// Tạo thư mục gốc upload nếu nó không tồn tại.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				for (MultipartFile fileData : fileDatas) {

					// Tên file gốc tại Client.
					String name = fileData.getOriginalFilename();
					System.out.println("Client File Name = " + name);

					if (name != null && name.length() > 0) {
						try {
							Files.deleteIfExists(Paths.get("uploads", doctors.getAva_url()));
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("AAA" + e);
							e.printStackTrace();
						}
						
						try {
							// Tạo file tại Server.
							String changeName = LocalTime.now().toString().replaceAll("[^A-Za-z0-9]", "");
							String nameupString = changeName + name;
							File serverFile = new File(
									uploadRootDir.getAbsolutePath() + File.separator + nameupString.toLowerCase());

							BufferedOutputStream stream = new BufferedOutputStream(
									new FileOutputStream(serverFile));
							stream.write(fileData.getBytes());
							stream.close();

							doctors.setAva_url(nameupString.toString());

							System.out.println("Write file: " + nameupString);
							System.out.println("Write file: " + serverFile);
						} catch (Exception e) {
							System.out.println("Error Write file: " + name);
						}
					}
				}
			}else {
				doctors.setAva_url(null);
			}

			doctors.setName(data.getName());
			doctors.setPhone(data.getPhone());
			doctors.setEmail(data.getEmail());
			doctors.setDescription(data.getDescription());
			doctors.setDuration(data.getDuration());
			doctors.setPrice(data.getPrice());
			doctors.setRating(data.getRating());
			doctors.setEnabled(data.getEnabled());

			Departments dep = departments;
			doctors.setDepartments(dep);

			session.saveOrUpdate(doctors);
			return doctors;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi ad doctor : " + e.getMessage());
			return null;
		}
	}
	
	public boolean deleteDoctor(int id_doctor) {
		Session session = sessionFactory.getCurrentSession();
		
		try {

			Doctors doctors = session.find(Doctors.class, id_doctor);
			session.delete(doctors);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi ad doctor : " + e.getMessage());
			return false;
		}
	}
}
