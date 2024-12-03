package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppl2.smartshop.entities.ProductOption;

public interface IProductOptionRepo extends JpaRepository<ProductOption, Long>{
	List<ProductOption> findByFeatureAndProductItemId(String feature,Long productId);
	List<ProductOption> findByProductItemId(Long productId);
}
