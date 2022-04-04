package com.huyhoang.covid19.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.huyhoang.covid19.entities.UserRela;
import com.huyhoang.covid19.entities.Users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;

	public Users getUser(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Users users = (Users) session.get(Users.class, id);
		return users;
	}

	public Users addUser(Users data) {
		Session session = sessionFactory.getCurrentSession();
		session.save(data);
		return data;
	}

	public Users updateUser(String username, Users data, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();
		Users user_token = authDAO.loadUsername(username);
		Date date = new Date();
		Users user = (Users) session.get(Users.class, user_token.getId());

		try {
			if (user_token.getId() == user.getId()) {

				MultipartFile[] fileDatas = files;

				if (fileDatas.length > 0) {
					System.out.println("AAA" + user.getAvatar_url());
					if (user.getAvatar_url() != null) {
						try {
							Files.deleteIfExists(Paths.get("uploads", user.getAvatar_url()));
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
									File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator
											+ nameupString.toLowerCase());

									BufferedOutputStream stream = new BufferedOutputStream(
											new FileOutputStream(serverFile));
									stream.write(fileData.getBytes());
									stream.close();

									user.setAvatar_url(nameupString);

									System.out.println("Write file: " + nameupString);
									System.out.println("Write file: " + serverFile);
								} catch (Exception e) {
									System.out.println("Error Write file: " + name);
								}
							}
						}
					}

				} else {
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

								user.setAvatar_url(nameupString);

								System.out.println("Write file: " + nameupString);
								System.out.println("Write file: " + serverFile);
							} catch (Exception e) {
								System.out.println("Error Write file: " + name);
							}
						}
					}
					user.setHeight(Integer.parseInt(data.getHeight().toString()));
					user.setWeight(Integer.parseInt(data.getWeight().toString()));

					user.setData_of_birth(data.getData_of_birth());
					user.setEmail(data.getEmail());
					user.setFull_name(data.getFull_name());
					user.setLocation(data.getLocation());
					user.setWork_at(data.getWork_at());
					user.setIs_active(true);
					user.setUpdated_at(date);

					session.saveOrUpdate(user);
					return user;
				}
			}
		} catch (

		Exception e) {
			// TODO: handle exception
			System.out.println("Loi user" + e);
			return null;
		}
		return user;
	}

	public boolean deleteUser(Integer id) {
		Session session = sessionFactory.openSession();
		boolean result = true;
		try {
			session.beginTransaction();
			Users user = (Users) session.get(Users.class, id);
			if (user != null) {
				session.delete(user);
				session.getTransaction().commit();
			} else {
				result = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		} finally {
			session.close();
		}
		return result;
	}

	public List<Users> getAllUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Users> listUsers = session.createQuery(" FROM Users").list();
		return listUsers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean userFollow(Users user, Integer id_follow) {
		Session session = sessionFactory.openSession();
		boolean result = true;
		try {
			session.beginTransaction();
			Users user_fl = session.get(Users.class, id_follow);

			String hqlString = "from UserRela where follower = :follower and following = :following";
			Query query = session.createQuery(hqlString, UserRela.class);
			query.setParameter("follower", user.getId());
			query.setParameter("following", id_follow);

			List<UserRela> uList = query.list();

			if (user_fl != null) {
				if (uList != null && uList.size() > 0) {
					result = false;
				} else {
					UserRela userRela = new UserRela();
					userRela.setFollower(user.getId());
					userRela.setFollowing(id_follow);
					session.save(userRela);
					session.getTransaction().commit();
					result = true;
				}

			} else {
				result = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result = false;
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean userUnFollow(Users user, Integer id_follow) {
		Session session = sessionFactory.openSession();
		boolean result = true;
		try {
			session.beginTransaction();
			Users user_fl = session.get(Users.class, id_follow);

			String hqlString = "from UserRela where follower = :follower and following = :following";
			Query query = session.createQuery(hqlString, UserRela.class);
			query.setParameter("follower", user.getId());
			query.setParameter("following", id_follow);

			List<UserRela> uList = query.list();

			if (user_fl != null) {
				if (uList != null && uList.size() > 0) {
					// System.out.println(uList.get(0) + "DAOOOOOOOOOOOOo");
					session.delete(uList.get(0));
					session.getTransaction().commit();
					result = true;
				} else {

					result = false;
				}

			} else {
				result = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result = false;
		} finally {
			session.close();
		}
		return result;
	}
	

}
