package com.huyhoang.covid19.dao;

import java.util.Date;
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
	
	public AppointmentOrder addAdAppointmentOrder(AppointmentOrder data, String username) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		AppointmentOrder appointmentOrder = new AppointmentOrder();
		try {
			appointmentOrder.setUser(user);
			appointmentOrder.setDoctor(data.getDoctor());
			appointmentOrder.setSlot(data.getSlot());
			appointmentOrder.setOrder_status(data.getOrder_status());
			appointmentOrder.setPayment_status(true);
			appointmentOrder.setCreated_at(date);
			session.save(appointmentOrder);
			return appointmentOrder;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
			
		}
	}
}
