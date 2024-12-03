package com.ppl2.smartshop.services;

import java.util.List;
import java.util.Map;

import com.ppl2.smartshop.entities.Category;
import com.ppl2.smartshop.model.CategoryDTO;

public interface ICategoryService extends IGeneralService<Category, Integer>{
	Map<String,List<CategoryDTO>> classify();
	void saveType(String type,String oldType);
	Map<String,List<CategoryDTO>> ListCategory(String keyword);
}
