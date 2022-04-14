package com.huyhoang.covid19.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import com.huyhoang.covid19.entities.AddNewsData;
import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News;
import com.huyhoang.covid19.entities.News_Img;
import com.huyhoang.covid19.entities.Users;

@Repository
public class NewsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<News> getAllNews(int position, int pageSize) {
		Session session = sessionFactory.getCurrentSession();

		try {
			String q = "from News where enabled = 1 order by id desc";
			Query query = session.createQuery(q, News.class);
			query.setFirstResult(position);
			query.setMaxResults(pageSize);
			System.out.println("AAAA" + query.list());
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public News addNews(String username, News data, Set<Category> listCate, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		News news = new News();
		Set<Category> list = listCate;

		Set<News_Img> listNews_Imgs = new HashSet<>();

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
					String changeName = LocalTime.now().toString().replaceAll("[^A-Za-z0-9]", "").replaceAll("\\s", "");
					String nameupString = changeName + name;
					File serverFile = new File(
							uploadRootDir.getAbsolutePath() + File.separator + nameupString.toLowerCase());

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					News_Img img = new News_Img();
					img.setName(nameupString);
					img.setNews(news);
					img.setCreated_at(date);
					img.setUpdated_at(date);
					listNews_Imgs.add(img);

					System.out.println("Write file: " + serverFile);
				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
				}
			}
		}

		try {
			news.setTitle(data.getTitle());
			news.setContent(data.getContent());
			news.setEnabled(true);
			news.setKeyword(data.getKeyword());
			news.setCreated_at(date);
			news.setUpdated_at(date);
			news.setCategories(list);
			news.setUser(user);
			news.setNews_Imgs(listNews_Imgs);
			session.save(news);

			System.out.println("NEWS" + news);
			return news;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
	}

	public News updateNews(String username,  AddNewsData data) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		
		
		Set<Category> newListCategory = data.getCategories();
		
		News aNews = data.getNews();
		try {
			News news = session.get(News.class, aNews.getId());
			news.setTitle(aNews.getTitle());
			news.setContent(aNews.getContent());
			news.setEnabled(aNews.getEnabled());
			news.setKeyword(aNews.getKeyword());
			news.setUpdated_at(date);
			
			news.setCategories(newListCategory);
			news.setUser(user);
			session.update(news);
			return news;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("NEWS "+ e);
			return null;
		}
	}

	public Boolean deleteNews(Integer id_news) {
		Session session = sessionFactory.getCurrentSession();

		try {
			News news = session.get(News.class, id_news);
			Set<News_Img> news_Imgs = news.getNews_Imgs();

			for (News_Img img : news_Imgs) {
				try {
					Files.deleteIfExists(Paths.get("uploads", img.getName()));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			session.delete(news);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
