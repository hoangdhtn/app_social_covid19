package com.huyhoang.covid19.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "ava_url")
	private String ava_url;

	@Column(name = "description")
	private String description;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "price")
	private Integer price;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private Departments departments;
	
	public Doctors() {
	}

	public Doctors(String name, String phone, String email, String ava_url, String description, Integer duration,
			Integer price, Boolean enabled) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.ava_url = ava_url;
		this.description = description;
		this.duration = duration;
		this.price = price;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAva_url() {
		return ava_url;
	}

	public void setAva_url(String ava_url) {
		this.ava_url = ava_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	
}
