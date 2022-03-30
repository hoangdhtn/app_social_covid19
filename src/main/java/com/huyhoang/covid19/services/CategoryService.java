package com.huyhoang.covid19.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huyhoang.covid19.dao.CategoryDAO;
import com.huyhoang.covid19.entities.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public List<Category> getAllCategory(){
		return categoryDAO.getAllCategory();
	}
	
	public Category addCategory(Category data) {
		return categoryDAO.addCategory(data);
	}
	
	public Category updateCategory(Category data) {
		return categoryDAO.updateCategory(data);
	}
	
	public Boolean deleteCategory(Integer id_cate) {
		return categoryDAO.deleteCategory(id_cate);
	}
}
