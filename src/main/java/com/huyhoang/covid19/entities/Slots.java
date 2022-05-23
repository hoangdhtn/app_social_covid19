package com.huyhoang.covid19.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "slots")
public class Slots {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@JsonFormat(pattern = "yy/MM/dd")
	@Column(name = "date")
	private String date;

	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "begin_at")
	private String begin_at;

	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "end_at")
	private String end_at;

	@Column(name = "index_day")
	private int index_day;

	@Column(name = "enabled")
	private boolean enabled;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctors doctor;

	// Appointment
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "slot")
	@JsonIgnore
	private Set<AppointmentOrder> appointmentOrders = new HashSet<>();

	public Slots() {

	}

	public Slots(String date, String begin_at, String end_at, int index_day, boolean enabled) {
		this.date = date;
		this.begin_at = begin_at;
		this.end_at = end_at;
		this.index_day = index_day;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBegin_at() {
		return begin_at;
	}

	public void setBegin_at(String begin_at) {
		this.begin_at = begin_at;
	}

	public String getEnd_at() {
		return end_at;
	}

	public void setEnd_at(String end_at) {
		this.end_at = end_at;
	}

	public int getIndex_day() {
		return index_day;
	}

	public void setIndex_day(int index_day) {
		this.index_day = index_day;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public Set<AppointmentOrder> getAppointmentOrders() {
		return appointmentOrders;
	}

	public void setAppointmentOrders(Set<AppointmentOrder> appointmentOrders) {
		this.appointmentOrders = appointmentOrders;
	}

	
}
