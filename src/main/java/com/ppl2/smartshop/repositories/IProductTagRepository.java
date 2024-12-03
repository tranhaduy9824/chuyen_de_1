package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ppl2.smartshop.entities.ProductTag;

@Repository
public interface IProductTagRepository extends JpaRepository<ProductTag, Long> {
	List<ProductTag> findAll();

	ProductTag findById(long id);

	void deleteById(long id);

	@Query("SELECT p FROM ProductTag p WHERE p.shop.id = :shopId")
	List<ProductTag> findByShopId(@Param("shopId") String string);

//	@Query("SELECT p FROM ProductTag p WHERE p.tagName = :oldType")
//	ProductTag findByTagName(@Param("oldType") String oldType);
	
}
