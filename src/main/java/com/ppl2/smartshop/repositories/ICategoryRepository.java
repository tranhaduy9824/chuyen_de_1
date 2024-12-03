package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppl2.smartshop.entities.Category;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findAll();

	Category findById(long id);

	void deleteById(long id);
}
