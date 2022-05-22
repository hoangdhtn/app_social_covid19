package com.huyhoang.covid19.dao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.Doctors;
import com.huyhoang.covid19.entities.ListTimeAvailable;
import com.huyhoang.covid19.entities.Slots;
import com.huyhoang.covid19.entities.TimeAvailable;

@Repository
public class SlotsDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Slots> getAllSlot(){
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Slots";
		Query query = session.createQuery(hql, Slots.class);

		List<Slots> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Slots> getSlotBusyByDoctor(int id_doctor){
		Session session = sessionFactory.getCurrentSession();

		String hql = "SELECT n FROM Slots n LEFT JOIN n.doctor c WHERE c.id = :id_doctor";
		Query query = session.createQuery(hql);
		query.setParameter("id_doctor", id_doctor);

		List<Slots> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
	
	// Lấy danh sách booking
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ListTimeAvailable getSlotByDoctor(int id_doctor, String date){
		Session session = sessionFactory.getCurrentSession();
		Session sessionSlot = sessionFactory.getCurrentSession();
		Doctors doctors;
		
		ListTimeAvailable listTimeAvailable = new ListTimeAvailable();
		ArrayList<TimeAvailable> sTimeAvai = new ArrayList<>();
		// doctor
		doctors = session.find(Doctors.class, id_doctor);
		// slot
		String hql = "FROM Slots WHERE date = :date";
		Query query = sessionSlot.createQuery(hql);
		query.setParameter("date", date);
		List<Slots> listSlots = query.list();
		
		
		int gapInMinutes =  doctors.getDuration() ;  // Define your span-of-time.
	    int loops = ( (int) Duration.ofHours( 24 ).toMinutes() / gapInMinutes ) ;
	    List<LocalTime> times = new ArrayList<>( loops ) ; // Mảng thời gian chia ra theo từng tiếng
	 
	    LocalTime time =  LocalTime.MIN ;  // '00:00'
	    for( int i = 1 ; i <= loops ; i ++ ) {
	    	TimeAvailable timeAvailable = new TimeAvailable();
	        times.add( time ) ;
	        timeAvailable.setIndex(i - 1);
	    	timeAvailable.setTime(time);
	        // Set up next loop.
	    	sTimeAvai.add(timeAvailable);
	        time = time.plusMinutes( gapInMinutes ) ;
	        
	    }
	 
	    System.out.println( times.size() + " time slots: " ) ;
	    System.out.println( times ) ;
	   
	    // Nếu ngày này đã có lịch
	    if(listSlots != null && listSlots.size() > 0) {
	    	List<Slots> listSlotsBusy = new ArrayList<>();
	    	listSlotsBusy = getSlotBusyByDoctor(id_doctor);
		    
	    	// Kiểm tra xem khoản thời gian nào đã tồn tại thì xóa trong mảng times
		    for(int i = 0; i < times.size(); i++) {
		    	for(int j = 0; j < listSlotsBusy.size(); j++) {
		    		LocalTime aLocalTime = LocalTime.parse(listSlotsBusy.get(j).getBegin_at());
		    		//System.out.println(times.get(i).compareTo(aLocalTime));
		    		if(times.get(i).compareTo(aLocalTime) == 0) {
		    			times.remove(i);
		    			sTimeAvai.remove(i);
		    		}
		    	}
		    }
	    }
	    
	    
	    listTimeAvailable.setListime(sTimeAvai);
		
	    System.out.println( times.size() + " time slots: " ) ;
	    System.out.println( times ) ;
	    return listTimeAvailable;
	}
	
	public Slots bookingSlot(Slots data) {
		Session session = sessionFactory.getCurrentSession();
		Session sessionSlot = sessionFactory.getCurrentSession();
		Slots slot = new Slots();
		
		
		try {
			String hql = "SELECT n FROM Slots n LEFT JOIN n.doctor c WHERE c.id = :id_doctor AND n.date = :date AND n.begin_at = :begin_at";
			Query query = sessionSlot.createQuery(hql);
			query.setParameter("id_doctor", data.getDoctor().getId());
			query.setParameter("date", data.getDate());
			query.setParameter("begin_at", data.getBegin_at());
			List<Slots> listSlots = query.list();
			
			
			if(listSlots != null && listSlots.size() > 0) {
				slot = null;
			}else {
				slot.setDate(data.getDate());
				slot.setBegin_at(data.getBegin_at());
				slot.setEnd_at(data.getEnd_at());
				slot.setIndex_day(data.getIndex_day());
				slot.setDoctor(data.getDoctor());
				slot.setEnabled(true);
				
				session.save(slot);
			}
		} catch (Exception e) {
			// TODO: handle exception
			slot = null;
		}
		return slot;
	}
	
	public boolean deleteBookingSlot(int id_slot) {
		Session session = sessionFactory.getCurrentSession();
		boolean result;
		try {
			Slots slots = session.find(Slots.class, id_slot);
			session.delete(slots);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		}
		return result;
	}
	
}
