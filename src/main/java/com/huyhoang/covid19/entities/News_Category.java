package com.huyhoang.covid19.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "news_category")
public class News_Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_news")
	private Integer id_news;
	
	@Column(name = "id_category")
	private Integer id_category;
	
	public News_Category() {
		
	}

	

	public News_Category(Integer id, Integer id_news, Integer id_category) {
		this.id = id;
		this.id_news = id_news;
		this.id_category = id_category;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_news() {
		return id_news;
	}

	public void setId_news(Integer id_news) {
		this.id_news = id_news;
	}

	public Integer getId_category() {
		return id_category;
	}

	public void setId_category(Integer id_category) {
		this.id_category = id_category;
	}
	
	
}
