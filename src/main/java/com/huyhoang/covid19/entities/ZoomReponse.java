package com.huyhoang.covid19.entities;

public class ZoomReponse {
	String topic;
	String status;
	String start_time;
	String duration;
	String agenda;
	String start_url;
	String join_url;
	String password;
	
	public ZoomReponse() {
		// TODO Auto-generated constructor stub
	}

	public ZoomReponse(String topic, String status, String start_time, String duration, String agenda, String start_url,
			String join_url, String password) {
		this.topic = topic;
		this.status = status;
		this.start_time = start_time;
		this.duration = duration;
		this.agenda = agenda;
		this.start_url = start_url;
		this.join_url = join_url;
		this.password = password;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getStart_url() {
		return start_url;
	}

	public void setStart_url(String start_url) {
		this.start_url = start_url;
	}

	public String getJoin_url() {
		return join_url;
	}

	public void setJoin_url(String join_url) {
		this.join_url = join_url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
