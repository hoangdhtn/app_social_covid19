package com.huyhoang.covid19.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Posts_Img;
import com.huyhoang.covid19.entities.Users;

@Repository
public class PostsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts> getAllPosts() {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Posts where enabled = 1 order by id desc";
		Query query = session.createQuery(hql, Posts.class);

		List<Posts> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts> getAllPostsUser(Integer id_user) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Posts where enabled = 1 and id_user = :id_user order by id desc";
		Query query = session.createQuery(hql, Posts.class);
		query.setParameter("id_user", id_user);
		List<Posts> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts> getAllPostsInWall(Integer id_user) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Posts where id_user = :id_user order by id desc";
		Query query = session.createQuery(hql, Posts.class);
		query.setParameter("id_user", id_user);

		List<Posts> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public Posts addPost(Integer id_user, Posts data, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();

		Date date = new Date();
		Posts post = new Posts();

		Set<Posts_Img> posts_Imgs = new HashSet<>();

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
					Posts_Img img = new Posts_Img();
					img.setName(nameupString);
					img.setPost(post);
					img.setCreated_at(date);
					img.setUpdated_at(date);
					posts_Imgs.add(img);

					System.out.println("Write file: " + serverFile);
				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
				}
			}
		}

		System.out.println("Hinh anh ne : " + posts_Imgs);
		try {
			post.setContent(data.getContent());
			post.setUser(session.get(Users.class, id_user));
			post.setEnabled(true);
			post.setCreated_at(date);
			post.setUpdated_at(date);
			post.setPosts_imgs(posts_Imgs);
			session.save(post);
			return post;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi ne : " + e);
			return null;

		}

	}
}
