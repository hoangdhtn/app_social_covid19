package com.huyhoang.covid19.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Posts;

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
}
