package com.huyhoang.covid19.services;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;


import com.huyhoang.covid19.entities.AddNewsData;
import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.entities.News;

public interface NewsService {

	public List<News> getAllNews(int position, int pageSize);
	
	public News addNews(String username, News data, Set<Category> listCate, MultipartFile[] files);
	
	public News updateNews(String username, AddNewsData data);
	
	public Boolean deleteNews(Integer id_news);
	
	public List<Object[]> getNewsByCate(int id_cate,int position, int pageSize );
}
