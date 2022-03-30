package com.huyhoang.covid19.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News;
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
			return query.list();		
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public News addNews(String username, News data, Set<Category> listCate) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		News news = new News();
		Set<Category> list = listCate;
		try {
			news.setTitle(data.getTitle());
			news.setContent(data.getContent());
			news.setEnabled(true);
			news.setKeyword(data.getKeyword());
			news.setCreated_at(date);
			news.setUpdated_at(date);
			news.setCategories(list);
			news.setUser(user);
			session.save(news);
			return news;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public News updateNews(String username, News data, Set<Category> listCate) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		Set<Category> list = listCate;
		try {
			News news = session.get(News.class, data.getId());
			news.setTitle(data.getTitle());
			news.setContent(data.getContent());
			news.setEnabled(data.getEnabled());
			news.setKeyword(data.getKeyword());
			news.setUpdated_at(date);
			news.setCategories(list);
			news.setUser(user);
			session.update(news);
			return news;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Boolean deleteNews(Integer id_news) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			News news = session.get(News.class, id_news);
			session.delete(news);
			return true;	
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
