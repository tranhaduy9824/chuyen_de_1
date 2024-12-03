package com.ppl2.smartshop.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.Category;
import com.ppl2.smartshop.model.CategoryDTO;
import com.ppl2.smartshop.repositories.ICategoryRepo;
@Service
public class CategoryService implements ICategoryService{
	@Autowired
	private ICategoryRepo categoryRepo;
	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public Category findById(Integer id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public Category save(Category t) {
		return categoryRepo.save(t);
	}

	@Override
	public void remove(Integer id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public Map<String,List<CategoryDTO>> classify() {
		Map<String , List<CategoryDTO>> types=new HashMap<String, List<CategoryDTO>>();
		List<Category> categories=categoryRepo.findAll();
		for(Category category:categories) {
			CategoryDTO dto=new CategoryDTO();
			
			dto.setId(category.getId());
			dto.setCode(category.getName());
			if(category.getName().contains("-")) {
				String[] type=category.getName().split("-",2);
				dto.setType(type[0]);
				dto.setName(type[1]);

			}else {
				dto.setName(category.getName());
				dto.setType(category.getName());
			}
			if(!types.containsKey(dto.getType())) {
				types.put(dto.getType(),new ArrayList<CategoryDTO>());
			}
			types.get(dto.getType()).add(dto);

		}
		return types;
	}

	@Override
	public void saveType(String type,String oldType) {
		categoryRepo.saveAll(categoryRepo.findByNameContaining(oldType).stream().map(category->{
			String[] categories=category.getName().split("-",2);
			categories[0]=type;
			category.setName(String.join("-", categories));
			return category;
		}).collect(Collectors.toList()));

		
	}
	@Override
	public  Map<String,List<CategoryDTO>> ListCategory(String keyword) {	
		Map<String , List<CategoryDTO>> types=new HashMap<String, List<CategoryDTO>>();
		List<Category> categories = categoryRepo.findByNameContaining(keyword);
		for(Category category:categories) {
			CategoryDTO dto=new CategoryDTO();
			
			dto.setId(category.getId());
			dto.setCode(category.getName());
			if(category.getName().contains("-")) {
				String[] type=category.getName().split("-",2);
				dto.setType(type[0]);
				dto.setName(type[1]);

			}else {
				dto.setName(category.getName());
				dto.setType(category.getName());
			}
			if(!types.containsKey(dto.getType())) {
				types.put(dto.getType(),new ArrayList<CategoryDTO>());
			}
			types.get(dto.getType()).add(dto);

		}
		return types;
	}

}
