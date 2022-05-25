package com.huyhoang.covid19.dao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huyhoang.covid19.entities.Departments;
import com.huyhoang.covid19.entities.Doctors;
import com.huyhoang.covid19.entities.ListTimeAvailable;
import com.huyhoang.covid19.entities.Slots;
import com.huyhoang.covid19.entities.TimeAvailable;
import com.huyhoang.covid19.entities.Users;
import com.huyhoang.covid19.entities.ZoomReponse;
import com.huyhoang.covid19.entities.ZoomRequest;

@Repository
public class SlotsDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;

	@Autowired
	public JavaMailSender emailSender;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Slots> getAllSlot() {
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
	public List<Slots> getSlotBusyByDoctor(int id_doctor) {
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Slots> getSlotsUser(String username) {
		Session session = sessionFactory.getCurrentSession();
		Users user = authDAO.loadUsername(username);
		String hql = "SELECT n FROM Slots n LEFT JOIN n.user c WHERE c.id = :id_user";
		Query query = session.createQuery(hql);
		query.setParameter("id_user",  user.getId());

		List<Slots> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	// Lấy danh sách booking
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<TimeAvailable> getSlotByDoctor(int id_doctor, String date) {
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

		int gapInMinutes = doctors.getDuration(); // Define your span-of-time.
		int loops = ((int) Duration.ofHours(24).toMinutes() / gapInMinutes);
		List<LocalTime> times = new ArrayList<>(loops); // Mảng thời gian chia ra theo từng tiếng

		LocalTime time = LocalTime.MIN; // '00:00'
		for (int i = 1; i <= loops; i++) {
			TimeAvailable timeAvailable = new TimeAvailable();
			times.add(time);
			timeAvailable.setIndex(i - 1);
			timeAvailable.setBegin_at(time);
			// Set up next loop.
			sTimeAvai.add(timeAvailable);
			time = time.plusMinutes(gapInMinutes);
			timeAvailable.setEnd_at(time);

		}

		System.out.println(times.size() + " time slots: ");
		System.out.println(times);

		// Nếu ngày này đã có lịch
		if (listSlots != null && listSlots.size() > 0) {
			List<Slots> listSlotsBusy = new ArrayList<>();
			listSlotsBusy = getSlotBusyByDoctor(id_doctor);

			// Kiểm tra xem khoản thời gian nào đã tồn tại thì xóa trong mảng times
			for (int i = 0; i < times.size(); i++) {
				for (int j = 0; j < listSlotsBusy.size(); j++) {
					LocalTime aLocalTime = LocalTime.parse(listSlotsBusy.get(j).getBegin_at());
					// System.out.println(times.get(i).compareTo(aLocalTime));
					if (times.get(i).compareTo(aLocalTime) == 0) {
						times.remove(i);
						sTimeAvai.remove(i);
					}
				}
			}
		}

		// listTimeAvailable.setListime(sTimeAvai);

		System.out.println(times.size() + " time slots: ");
		System.out.println(times);
		return sTimeAvai;
	}

	// Đặt lịch
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Slots bookingSlot(Slots data, String username) {
		Session session = sessionFactory.getCurrentSession();
		Session sessionSlot = sessionFactory.getCurrentSession();
		Session sessionDoctor = sessionFactory.getCurrentSession();
		Slots slot = new Slots();
		Date date = new Date();

		Users user = authDAO.loadUsername(username);
		Doctors doctors = sessionDoctor.find(Doctors.class, data.getDoctor().getId());

		try {
			String hql = "SELECT n FROM Slots n LEFT JOIN n.doctor c WHERE c.id = :id_doctor AND n.date = :date AND n.begin_at = :begin_at";
			Query query = sessionSlot.createQuery(hql);
			query.setParameter("id_doctor", doctors.getId());
			query.setParameter("date", data.getDate());
			query.setParameter("begin_at", data.getBegin_at());
			List<Slots> listSlots = query.list();

			if (listSlots != null && listSlots.size() > 0) {
				slot = null;
			} else {

				slot.setDate(data.getDate());
				slot.setBegin_at(data.getBegin_at());
				slot.setEnd_at(data.getEnd_at());
				slot.setIndex_day(data.getIndex_day());
				slot.setDoctor(doctors);
				slot.setUser(user);
				slot.setEnabled(true);
				slot.setOrder_info(data.getOrder_info());
				slot.setPayment_status(true);
				slot.setCreated_at(date);
				slot.setUpdated_at(null);

				session.save(slot);

				// ======== TẠO ROOM ZOOM API ===========
				String tokenZoomString = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6ImxPcUJtY3lzUVgySUNnRFM0dGlNQ0EiLCJleHAiOjE3NjcwNzA4MDAsImlhdCI6MTY1MzQ0MzE2MH0.dux-zaKbjTckBh-5PGhkPkrzLiQCJZQ9ED_8j0uehcY";
				// request url
				String url = "https://api.zoom.us/v2/users/IU6NJ328RH2HuK3fNRM0nQ/meetings";

				// create an instance of RestTemplate
				RestTemplate restTemplate = new RestTemplate();

				// create headers
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBearerAuth(tokenZoomString);

				ZoomRequest zoomRequest = new ZoomRequest();
				zoomRequest.setAgenda("HOVA - Kính mời bạn");
				zoomRequest.setDuration(doctors.getDuration().toString());
				zoomRequest.setStart_time(data.getDate() + "T" + data.getBegin_at() + "Z");
				zoomRequest.setTimezone("Asia/Saigon");
				zoomRequest.setTopic("Tư vấn về sức khỏe của bạn");

				// build the request
				HttpEntity request = new HttpEntity<>(zoomRequest, headers);
				// send POST request
				ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

				// check response
				if (response.getStatusCode() == HttpStatus.CREATED) {
					// Chuyển đổi String -> JSON -> lấy từng phần tử gán vào Entity
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode jsonNode = objectMapper.readTree(response.getBody());

					ZoomReponse zoomReponse = new ZoomReponse();
					zoomReponse.setAgenda(jsonNode.get("agenda").textValue());
					zoomReponse.setDuration(jsonNode.get("duration").textValue());
					zoomReponse.setJoin_url(jsonNode.get("join_url").textValue());
					zoomReponse.setStart_time(jsonNode.get("start_time").textValue());
					zoomReponse.setStart_url(jsonNode.get("start_url").textValue());
					zoomReponse.setPassword(jsonNode.get("password").textValue());
					zoomReponse.setStatus(jsonNode.get("status").textValue());
					zoomReponse.setTopic(jsonNode.get("topic").textValue());

					System.out.println("Zoom Created");
					System.out.println(response);
					System.out.println("PASS" + zoomReponse.getPassword());

					// =========== GỬI MAIL CHO NGƯỜI DÙNG =======
					MimeMessage message = emailSender.createMimeMessage();

					boolean multipart = true;

					MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "UTF-8");

					String htmlMsg = "<h3>" + zoomReponse.getAgenda() + "</h3></br>" + "<p>" + zoomReponse.getTopic()
							+ "</p></br>" + "<p>Thời gian tư vấn: " + doctors.getDuration() + " phút</p></br>"
							+ "<p>Thời gian bắt đầu: " + data.getDate() +" - " +data.getBegin_at() + "</p></br>"
							+ "<p>Tên bác sỹ: " + doctors.getName() + "</p></br>"
							+ "<p>Link tham gia website ZOOM: " + zoomReponse.getStart_url() + "</p></br>"
							+ "<p>Link tham gia ứng dụng ZOOM: " + zoomReponse.getJoin_url() + "</p></br>"
							+ "<p>Mật khẩu tham gia: " + zoomReponse.getPassword() + "</p></br>"
							+ "<p>Trạng thái: " + zoomReponse.getStatus() + "</p></br>";
					
					message.setContent(htmlMsg, "text/html; charset=UTF-8");
					helper.setTo(user.getEmail());
					helper.setSubject("[HOVA-Ứng dụng sức khỏe] Lịch hẹn tư vấn của bạn!");
					this.emailSender.send(message);
					
					System.out.println("Sent mail success");
					// ===========  END GỬI MAIL CHO NGƯỜI DÙNG =======
				} else {
					System.out.println("Request Failed");
					System.out.println(response.getStatusCode());
				}
				// ======== END TẠO ROOM ZOOM API ===========

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed" + e.getMessage());
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
