package com.huyhoang.covid19.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "medicalinfo")
public class MedicalInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_user")
	private Integer id_user;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "info")
	private String info;
	
	@Column(name = "enabled", columnDefinition = "TINYINT(1)")
	private Boolean enabled;
	
	@JsonFormat(pattern = "yy/MM/dd")
	@Column(name = "created_at")
	private String created_at;

	@JsonFormat(pattern = "yy/MM/dd")
	@Column(name = "updated_at")
	private String updated_at;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "medicalinfo", cascade = CascadeType.ALL)
	private Set<MedicalInfo_Img> listImg = new HashSet<>();
	
	public MedicalInfo() {
		
	}

	public MedicalInfo(Integer id_user, String name, String info, Boolean enabled, String created_at, String updated_at) {
		this.id_user = id_user;
		this.name = name;
		this.info = info;
		this.enabled = enabled;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_user() {
		return id_user;
	}

	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public Set<MedicalInfo_Img> getListImg() {
		return listImg;
	}

	public void setListImg(Set<MedicalInfo_Img> listImg) {
		this.listImg = listImg;
	}

		
}
