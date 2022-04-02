package com.huyhoang.covid19.services;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huyhoang.covid19.dao.NewsDAO;
import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News;

@Service
@Transactional
public class NewsService {

	@Autowired
	private NewsDAO newsDAO;
	
	public List<News> getAllNews(int position, int pageSize) {
		return newsDAO.getAllNews(position, pageSize);
	}
	
	public News addNews(String username, News data, Set<Category> listCate) {
		return newsDAO.addNews(username, data, listCate);
	}
	
	public News updateNews(String username, News data, Set<Category> listCate) {
		return newsDAO.updateNews(username, data, listCate);
	}
	
	public Boolean deleteNews(Integer id_news) {
		return newsDAO.deleteNews(id_news);
	}
}