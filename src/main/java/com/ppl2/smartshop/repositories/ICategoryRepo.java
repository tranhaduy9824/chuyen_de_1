package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppl2.smartshop.entities.Category;

public interface ICategoryRepo extends JpaRepository<Category, Integer> {
	List<Category> findByNameContaining(String type);

	
	
}
