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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Entity
@Table(name = "users")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "full_name")
	private String full_name;

	@Column(name = "avatar_url")
	private String avatar_url;

	@Column(name = "is_active", columnDefinition = "TINYINT(1)")
	private Boolean is_active;

	@Column(name = "work_at")
	private String work_at;

	@Column(name = "location")
	private String location;

	@Column(name = "data_of_birth")
	private Date data_of_birth;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;
	
	//Role
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "users_roles",
	joinColumns = {@JoinColumn(name = "user")},
	inverseJoinColumns = {@JoinColumn(name ="role")})
	private Set<Role> roles = new HashSet<>();
	
	
	//Post
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Posts> posts = new HashSet<>();
	
	
	public Users() {

	}

	public Users(String username, String password, String email, String full_name,
			String avatar_url, Boolean is_active, String work_at, String location, Date date, Date created_at,
			Date updated_at) {
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

	public Date getData_of_birth() {
		return data_of_birth;
	}

	public void setData_of_birth(Date data_of_birth) {
		this.data_of_birth = data_of_birth;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setPosts(Set<Posts> posts) {
		this.posts = posts;
	}

	

}
