package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ppl2.smartshop.entities.ProductItem;


@Repository
public interface IProductItemRepo extends JpaRepository<ProductItem, Long> {

	@Query(value = "SELECT * FROM product_item p where "
			+ "    (:city is null OR p.shop_id IN (SELECT s.id FROM shop s where position=:city)) "
			+ "AND (:scopeVoucher is null OR p.scope=:scopeVoucher) "
			+ "AND (:categoryId is null OR p.category_id=:categoryId) " + "AND (:max is null OR p.sale_price<=:max) "
			+ "AND (:min is null OR p.sale_price>=:min)", countQuery = "SELECT COUNT(p.id) FROM product_item p where "
					+ "    (:city is null OR p.shop_id IN (SELECT s.id FROM shop s where position=:city)) "
					+ "AND (:scopeVoucher is null OR p.scope=:scopeVoucher) "
					+ "AND (:categoryId is null OR p.category_id=:categoryId) "
					+ "AND (:max is null OR p.sale_price<=:max) "
					+ "AND (:min is null OR p.sale_price>=:min)", nativeQuery = true)
	Page<ProductItem> filterPage(@Param("city") String city, @Param("scopeVoucher") String scopeVoucher,
			@Param("categoryId") Integer categoryId, @Param("max") Integer max, @Param("min") Integer min,
			Pageable pageable);

	@Query(value = "SELECT * FROM product_item p where "

			+ "   (:city is null OR p.shop_id IN (SELECT s.id FROM shop s where position=:city)) "
			+ "AND (:scopeVoucher is null OR p.scope=:scopeVoucher) "
			+ "AND (:categoryId is null OR p.category_id=:categoryId ) " + "AND (:max is null OR p.sale_price<=:max) "
			+ "AND (:min is null OR p.sale_price>=:min) "
			+ "AND p.title like %:keyword% ", countQuery = "SELECT COUNT(p.id) FROM product_item p where "

					+ "   (:city is null OR p.shop_id IN (SELECT s.id FROM shop s where position=:city)) "
					+ "AND (:scopeVoucher is null OR p.scope=:scopeVoucher) "
					+ "AND (:categoryId is null OR p.category_id=:categoryId) "
					+ "AND (:max is null OR p.sale_price<=:max) " + "AND (:min is null OR p.sale_price>=:min) "
					+ "AND p.title like %:keyword% ", nativeQuery = true)

	Page<ProductItem> searchProduct(@Param("keyword") String keyword, @Param("city") String city,
			@Param("scopeVoucher") String scopeVoucher, @Param("categoryId") Integer categoryId,
			@Param("max") Integer max, @Param("min") Integer min, Pageable pageable);

	List<ProductItem> findTop10ByOrderByNumPurchasesDescRatingDesc();

	@Query(value = "SELECT p.* FROM product_item p LIMIT ?1", nativeQuery = true)
	List<ProductItem> findLimit(Long num);

	Page<ProductItem> findAllByTitleContaining(String searchTitle, PageRequest pageRequest);

	@Query("select p from ProductItem p where p.tag.id=(:id)")
	List<ProductItem> findAllByTagId(@Param("id") long tagId);

	@Query("SELECT p FROM ProductItem p WHERE p.title LIKE %:searchTitle% AND p.shop.id = :shopId")
	Page<ProductItem> findAllByTitleContainingAndShop(@Param("searchTitle") String searchTitle,
			@Param("shopId") String shopId, Pageable pageable);

	@Query("SELECT p FROM ProductItem p WHERE p.shop.id = :shopId")
	Page<ProductItem> findAllByShop(@Param("shopId") String id, PageRequest of);

	@Query("SELECT p FROM ProductItem p WHERE p.shop.id = :shopId")
	List<ProductItem> findAllByShop(@Param("shopId") String id);

	@Query("select p from ProductItem p where p.tag.id=(:id)")
	Page<ProductItem> findAllByTag(@Param("id") Long tagId, Pageable pageable);

	@Query("SELECT p FROM ProductItem p WHERE p.shop.id = :shopId")
	Page<ProductItem> findAllByShop(@Param("shopId") String shopId, Pageable pageable);
	
	List<ProductItem> findTop6ByShopIdOrderByNumPurchasesDescRatingDesc(String shopId);
	
	Long countByShopId(String shopId);
}
