package com.huyhoang.covid19.dao;

import java.util.List;

import com.huyhoang.covid19.entities.UserRela;
import com.huyhoang.covid19.entities.Users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;

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

	public Users updateUser(Users data) {
		Session session = sessionFactory.getCurrentSession();
		Users user = (Users) session.get(Users.class, data.getId());
		user.setAvatar_url(data.getAvatar_url());
		user.setData_of_birth(data.getData_of_birth());
		user.setEmail(data.getEmail());
		user.setFull_name(data.getFull_name());
		user.setLocation(data.getLocation());
		user.setPassword(data.getPassword());
		user.setWork_at(data.getWork_at());
		user.setIs_active(data.getIs_active());

		session.update(user);
		return data;
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
						//System.out.println(uList.get(0) + "DAOOOOOOOOOOOOo");
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
