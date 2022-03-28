package com.huyhoang.covid19.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts_cmt")
public class Posts_Cmt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;
	
	
	
	// n - 1: Users
	@ManyToOne
	@JoinColumn(name = "id_user")
	private Users user;
	
	//n - 1: Post
	@ManyToOne
	@JoinColumn(name = "id_post")
	private Posts post;
	
	public Posts_Cmt() {
		
	}

	public Posts_Cmt(String content, Date created_at, Date updated_at) {
		this.content = content;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public void setUser(Users user) {
		this.user = user;
	}
	

	public Users getUser() {
		return user;
	}

	public void setPost(Posts post) {
		this.post = post;
	}
	
	
}
