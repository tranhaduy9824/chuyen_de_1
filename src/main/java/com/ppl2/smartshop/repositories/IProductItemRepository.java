package com.ppl2.smartshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ppl2.smartshop.entities.ProductItem;
public interface IProductItemRepository extends JpaRepository<ProductItem, Long > {

}
