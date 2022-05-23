package com.huyhoang.covid19.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.AppointmentOrder;
import com.huyhoang.covid19.entities.Users;

@Repository
public class AppointmentOrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AppointmentOrder> getMyListAppointmentOrders(String username) {
		Session session = sessionFactory.getCurrentSession();
		Session sessionUser = sessionFactory.getCurrentSession();

		Users user = authDAO.loadUsername(username);

		String hql = "FROM  AppointmentOrder n WHERE n.user ="
				+ user.getId().toString();
		Query query = session.createQuery(hql);

		List<AppointmentOrder> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
}
