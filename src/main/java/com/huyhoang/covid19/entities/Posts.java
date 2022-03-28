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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "content")
	private String content;
	

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;

	// n - 1 : User
	@ManyToOne()
	@JoinColumn(name = "id_user", nullable = false)
	private Users user;


	// 1 - n: Posts Img
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Posts_Img> posts_imgs = new HashSet<>();
	
	// 1 - n: Post Like
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Likes> likes = new HashSet<>();

	public Posts() {

	}

	public Posts(String content, Boolean enabled, Date created_at, Date updated_at) {
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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

	public Set<Posts_Img> getPosts_imgs() {
		return posts_imgs;
	}

	public void setPosts_imgs(Set<Posts_Img> posts_imgs) {
		this.posts_imgs = posts_imgs;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}

	public Users getUser() {
		return user;
	}

	public Set<Likes> getLikes() {
		return likes;
	}

	public void setLikes(Set<Likes> likes) {
		this.likes = likes;
	}

	
}
