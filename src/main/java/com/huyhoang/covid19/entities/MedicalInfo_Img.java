package com.huyhoang.covid19.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicalinfo_img")
public class MedicalInfo_Img implements java.io.Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_medicalinfo")
	private Integer id_medicalinfo;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;
	
	public MedicalInfo_Img() {
		
	}

	public MedicalInfo_Img(Integer id_medicalinfo, String name, Date created_at, Date updated_at) {
		this.id_medicalinfo = id_medicalinfo;
		this.name = name;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_medicalinfo() {
		return id_medicalinfo;
	}

	public void setId_medicalinfo(Integer id_medicalinfo) {
		this.id_medicalinfo = id_medicalinfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
}
