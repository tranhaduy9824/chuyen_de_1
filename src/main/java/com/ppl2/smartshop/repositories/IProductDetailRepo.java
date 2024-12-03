package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppl2.smartshop.entities.ProductDetail;


public interface IProductDetailRepo extends JpaRepository<ProductDetail, Long>{
	
	@Query("SELECT pdetail FROM ProductDetail pdetail JOIN pdetail.productOptions o WHERE pdetail.productItem.id=:productItemId AND o.id IN :options GROUP BY pdetail HAVING COUNT(DISTINCT o) = :optionCount")
	List<ProductDetail> findProductDetailsByOptions(@Param("productItemId") Long itemId,@Param("options") List<Long> options, @Param("optionCount") long optionCount);
	
	@Query("Select p from ProductDetail p Join p.productOptions o where o.id != ?1")
	List<ProductDetail> findByOption(Long optionId);
	
	Page<ProductDetail> findByProductItemShopId(String shopId, Pageable pageable);
	Page<ProductDetail> findByProductItemShopIdAndProductItemTitleContaining(String shopId, String keyword, Pageable pageable);
}
