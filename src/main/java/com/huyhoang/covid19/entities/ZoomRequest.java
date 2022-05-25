package com.huyhoang.covid19.entities;

public class ZoomRequest {
	String agenda;
	String duration;
	String contact_email;
	String contact_name;
	String start_time;
	String timezone;
	String topic;
	
	public ZoomRequest() {
		// TODO Auto-generated constructor stub
	}

	public ZoomRequest(String agenda, String duration, String contact_email, String contact_name,
			String start_time, String timezone, String topic) {
		this.agenda = agenda;
		this.duration = duration;
		this.contact_email = contact_email;
		this.contact_name = contact_name;
		this.start_time = start_time;
		this.timezone = timezone;
		this.topic = topic;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	
}