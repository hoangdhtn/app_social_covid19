package com.huyhoang.covid19.servicesimpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.dao.NewsDAO;
import com.huyhoang.covid19.entities.AddNewsData;
import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News;
import com.huyhoang.covid19.services.NewsService;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsDAO newsDAO;
	
	public List<News> getAllNews(int position, int pageSize) {
		return newsDAO.getAllNews(position, pageSize);
	}
	
	public News addNews(String username, News data, Set<Category> listCate, MultipartFile[] files) {
		return newsDAO.addNews(username, data, listCate, files);
	}
	
	public News updateNews(String username, AddNewsData data) {
		return newsDAO.updateNews(username, data);
	}
	
	public Boolean deleteNews(Integer id_news) {
		return newsDAO.deleteNews(id_news);
	}
	
	public List<Object[]> getNewsByCate(int id_cate,int position, int pageSize ){
		return newsDAO.getNewsByCate(id_cate, position, pageSize);
	}
}
