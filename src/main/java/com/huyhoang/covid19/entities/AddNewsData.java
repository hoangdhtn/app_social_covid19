package com.huyhoang.covid19.entities;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class AddNewsData {
	private News news;
	private MultipartFile[] files;
	private Set<Category> categories;

	public AddNewsData() {

	}

	public AddNewsData(News news, Set<Category> categories) {
		this.news = news;
		this.categories = categories;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}


	
}
