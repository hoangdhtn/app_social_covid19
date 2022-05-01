package com.huyhoang.covid19.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huyhoang.covid19.dao.CategoryDAO;
import com.huyhoang.covid19.entities.Category;
import com.huyhoang.covid19.services.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
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
