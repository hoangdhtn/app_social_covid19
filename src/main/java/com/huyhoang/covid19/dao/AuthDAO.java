package com.huyhoang.covid19.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Role;
import com.huyhoang.covid19.entities.Users;

@Repository
public class AuthDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Users loadUsername(final String username) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "from Users where username = :username";
		Query query = session.createQuery(hql, Users.class);
		query.setParameter("username", username);

		List<Users> users = query.list();

		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public boolean checkLogin(Users userForm) {	
		Users u = loadUsername(userForm.getUsername());
		
		//System.out.println(passwordEncoder.matches(userForm.getPassword(), u.getPassword()));

		if(u != null)
		{
			if (passwordEncoder.matches(userForm.getPassword(), u.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public Users signupUser(Users data) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Users where username = :username or email = :email";
		Query query = session.createQuery(hql, Users.class);
		query.setParameter("username", data.getUsername());
		query.setParameter("email", data.getEmail());

		List<Users> dd = query.list();

		if (dd != null && dd.size() > 0) {
			// System.out.print(data);
			return null;
		} else {
			if (data.getUsername() != null && data.getEmail() != null && data.getPassword() != null) {
				Session session2 = sessionFactory.openSession();
				try {
					session2.beginTransaction();

					Users user = new Users();
					user.setUsername(data.getUsername());
					user.setPassword(passwordEncoder.encode(data.getPassword()));
					user.setEmail(data.getEmail());
					user.setIs_active(true);

					Set<Role> roles = new HashSet<>();
					roles.add(session2.get(Role.class, 3));
					roles.add(session2.get(Role.class, 4));

					user.setRoles(roles);
					session2.save(user);
					session2.getTransaction().commit();
					return user;

				} catch (Exception e) {
					// TODO: handle exception
					session2.getTransaction().rollback();
					e.printStackTrace();
				}
			}
		}
		return null;

	}
}
