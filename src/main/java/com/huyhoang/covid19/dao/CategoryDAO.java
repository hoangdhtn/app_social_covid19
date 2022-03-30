package com.huyhoang.covid19.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News_Category;

@Repository
public class CategoryDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Category> getAllCategory(){
		Session session = sessionFactory.getCurrentSession();
		try {
			String q = "from Category";
			Query query = session.createQuery(q, Category.class);
			List<Category> list = query.list();
			return list;		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		
	}
	
	public Category addCategory(Category data) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			Category category = new Category();
			category.setName(data.getName());
			category.setEnabled(true);
			category.setCreated_at(date);
			category.setUpdated_at(date);
			session.save(category);
			return category;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Category updateCategory(Category data) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			Category category = session.get(Category.class, data.getId());
			category.setName(data.getName());
			category.setEnabled(true);
			category.setCreated_at(date);
			category.setUpdated_at(date);
			session.update(category);
			return category;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean deleteCategory(Integer id_cate) {
		
		
		try {
			Session session = sessionFactory.getCurrentSession();
			String q = "from News_Category where id_category = :id_cate";
			Query query = session.createQuery(q, News_Category.class);
			query.setParameter("id_cate", id_cate);
			
			List<News_Category> list = query.list();
			
			
			for(News_Category n : list) {
				Session session3 = sessionFactory.getCurrentSession();
				int a = n.getId();
				session3.clear();
				session3.createQuery("delete from News_Category where id_category = :id_cate")
				.setParameter("id_cate", a).executeUpdate();
			}
			
			Session session2 = sessionFactory.getCurrentSession();
			Category category = (Category)session2.get(Category.class, id_cate);
			session2.delete(category);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return false;
		}
	}
}
