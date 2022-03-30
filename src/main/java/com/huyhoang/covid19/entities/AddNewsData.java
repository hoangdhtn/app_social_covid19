package com.huyhoang.covid19.entities;

import java.util.Set;

public class AddNewsData {
	private News news;
	private Set<Category> category;

	public AddNewsData() {

	}

	public AddNewsData(News news, Set<Category> category) {
		this.news = news;
		this.category = category;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

}
