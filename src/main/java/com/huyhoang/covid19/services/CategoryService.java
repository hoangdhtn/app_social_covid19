package com.huyhoang.covid19.services;

import java.util.List;


import com.huyhoang.covid19.entities.Category;


public interface CategoryService {

	public List<Category> getAllCategory();
	
	public Category addCategory(Category data);
	
	public Category updateCategory(Category data);
	
	public Boolean deleteCategory(Integer id_cate);
}