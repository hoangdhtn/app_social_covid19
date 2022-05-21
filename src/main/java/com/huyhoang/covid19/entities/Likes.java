package com.huyhoang.covid19.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_user")
	private Integer id_user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_post", nullable = false)
	private Posts post;
	
	public Likes() {
		
	}

	public Likes(Integer id_user, Posts post) {
		this.id_user = id_user;
		this.post = post;
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

//	public Posts getPost() {
//		return post;
//	}

	public void setPost(Posts post) {
		this.post = post;
	}
	
	
}
