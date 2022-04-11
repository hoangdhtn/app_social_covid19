package com.huyhoang.covid19.controllers;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huyhoang.covid19.entities.Email;
import com.huyhoang.covid19.entities.Users;

@RestController
@RequestMapping("/api")
@Transactional
public class MailController {

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sentmail", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<Email> sentMail(@RequestBody Email email) {
		HttpStatus httpStatus = null;
		String result = "";
		Email emailResult = new Email();
		try {
			Users users = new Users();
			int max = 9999;
			int min = 1000;
			int random = (int)(Math.random() * (max - min + 1) + min);

			Session session = sessionFactory.getCurrentSession();
			String q = "from Users where email = :email";
			Query query = session.createQuery(q, Users.class);
			query.setParameter("email", email.getEmail());

			List<Users> list = query.list();

			if (list != null && list.size() > 0) {
				users = list.get(0);
				System.out.print("AA" + users.getEmail());
				
				session.clear();
				Session session2 = sessionFactory.getCurrentSession();
				Users users2 = session2.get(Users.class, users.getId());
				users2.setForget_pass_key(Integer.toString(random));
				session2.update(users2);
				
				SimpleMailMessage message = new SimpleMailMessage();

				message.setTo(users.getEmail());
				
				message.setSubject("[HOVA-Ứng dụng sức khỏe] Mật mã reset mật khẩu của bạn!");
				message.setText("Mật mã: " + Integer.toString(random));

				// Send Message!
				this.emailSender.send(message);
				
				httpStatus = HttpStatus.OK;
				result = "Sent mail success";
				emailResult.setStatus("success");
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
				result = "Sent mail fail";
				emailResult.setStatus("fail");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Loi email" + e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Email>(emailResult, httpStatus);
	}
}
