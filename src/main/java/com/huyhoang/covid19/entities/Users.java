package com.huyhoang.covid19.entities;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username")
	@NotEmpty(message = "Thiếu username")
	private String username;

	@Column(name = "password")
	@NotEmpty(message = "Thiếu password")
	@Length(min = 8, message = "Password phải từ 8 kí tự trở lên")
	private String password;

	@Column(name = "email")
	@Email(message = "Email không hợp lệ")
	private String email;

	@Column(name = "full_name")
	private String full_name;

	@Column(name = "height")
	private Integer height;

	@Column(name = "weight")
	private Integer weight;

	@Column(name = "avatar_url")
	private String avatar_url;

	@Column(name = "is_active", columnDefinition = "TINYINT(1)")
	private Boolean is_active;

	@Column(name = "work_at")
	private String work_at;

	@Column(name = "location")
	private String location;

	@JsonFormat(pattern = "yy/MM/dd")
	@Column(name = "data_of_birth")
	private String data_of_birth;

	@Column(name = "forget_pass_key")
	private String forget_pass_key;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;

	// Role
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user") }, inverseJoinColumns = {
			@JoinColumn(name = "role") })
	private Set<Role> roles = new HashSet<>();

	// Post
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Posts> posts = new HashSet<>();

	// Comment post
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Posts_Cmt> posts_Cmts = new HashSet<>();

	// News
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<News> news = new HashSet<>();

	// SLot
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonIgnore
	private Set<Slots> slots = new HashSet<>();

	public Users() {

	}

	public Users(String username, String password, String email, String full_name, String avatar_url, Boolean is_active,
			String work_at, String location, String date, Date created_at, Date updated_at) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.full_name = full_name;
		this.avatar_url = avatar_url;
		this.is_active = is_active;
		this.work_at = work_at;
		this.location = location;
		this.data_of_birth = date;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	@Transient
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role usersRoles : this.roles) {
			authorities.add(new SimpleGrantedAuthority(usersRoles.getName()));
		}
		return authorities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public String getWork_at() {
		return work_at;
	}

	public void setWork_at(String work_at) {
		this.work_at = work_at;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getData_of_birth() {
		return data_of_birth;
	}

	public void setData_of_birth(String data_of_birth) {
		this.data_of_birth = data_of_birth;
	}

	public String getForget_pass_key() {
		return forget_pass_key;
	}

	public void setForget_pass_key(String forget_pass_key) {
		this.forget_pass_key = forget_pass_key;
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

//	public Set<Role> getRoles() {
//		return roles;
//	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setPosts(Set<Posts> posts) {
		this.posts = posts;
	}

	public void setPosts_Cmts(Set<Posts_Cmt> posts_Cmts) {
		this.posts_Cmts = posts_Cmts;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	public void setSlots(Set<Slots> slots) {
		this.slots = slots;
	}

	public Set<Slots> getSlots() {
		return slots;
	}



}
