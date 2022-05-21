package com.huyhoang.covid19.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Likes;
import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Users;

@Repository
public class LikesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean likePost(String username, Integer id_post) {
		Session session = sessionFactory.getCurrentSession();

		Users user = authDAO.loadUsername(username);
		Posts post = session.get(Posts.class, id_post);

		String hqlString = "from Likes where id_post = :id_post";
		Query query = session.createQuery(hqlString, Likes.class);
		query.setParameter("id_post", id_post);

		List<Likes> list = query.list();

		if (list != null && list.size() > 0) {
			return false;
		} else {
			try {
				Likes like = new Likes();
				like.setId_user(user.getId());
				like.setPost(post);
				session.save(like);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean unlikePost(String username, Integer id_post) {
		Session session = sessionFactory.getCurrentSession();

		Users user = authDAO.loadUsername(username);

		String hqlString = "from Likes where id_post = :id_post";
		Query query = session.createQuery(hqlString, Likes.class);
		query.setParameter("id_post", id_post);

		List<Likes> list = query.list();

		if (list != null && list.size() > 0 && list.get(0).getId_user() == user.getId()) {
			session.clear();
			
			session.createQuery("delete from Likes where id_post = :id_post")
			.setParameter("id_post", id_post).executeUpdate();
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Likes> getLikePost(String username, Integer id_post) {
		Session session = sessionFactory.getCurrentSession();

		Users user = authDAO.loadUsername(username);
		
		String hqlString = "from Likes where id_post = :id_post";
		Query query = session.createQuery(hqlString, Likes.class);
		query.setParameter("id_post", id_post);
		
		List<Likes> list = query.list();
		
		if (list != null) {
			System.out.print("LIST LIKE SV" + list);
			return list;
		} else {
			return null;
		}
	}
}
