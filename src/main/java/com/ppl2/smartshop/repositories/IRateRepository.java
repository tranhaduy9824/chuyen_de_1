package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ppl2.smartshop.entities.Rate;

@Repository
public interface IRateRepository extends JpaRepository<Rate, Long> {
	@Query("select p from Rate p where p.productItem.id = :id")
    List<Rate> findByProductItemId(@Param("id") long id);
	Long countByProductItemShopId(String shopId);
}
