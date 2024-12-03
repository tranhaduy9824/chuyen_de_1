package com.ppl2.smartshop.repositories;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ppl2.smartshop.entities.Shop;

@Repository
public interface IShopRepository extends JpaRepository<Shop, String>{
	List<Shop> findAll();
	@Query("Select p.productItem.shop from ProductDetail p where p.id in ?1")
	Set<Shop> findBydetailId(List<Long> ids);
	List<Shop> findTop10By();
    
    void deleteById(long id);

    @Query("SELECT s FROM Shop s WHERE s.user.id = :userId")
    Shop getShopByUserId(@Param("userId")Long userId);

}
