package com.ppl2.smartshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ppl2.smartshop.entities.OrderItem;

public interface IOrderItemRepo extends JpaRepository<OrderItem, Long>{
	OrderItem findByIdAndOrderCustomerUserUserId(Long orderId,Long userId);
	@Query("select o.productDetail.productItem.title,o.productDetail.productItem.image,count(o.id),sum(o.money) from OrderItem o "
			+ "where o.productDetail.productItem.shop.id=?1 "
			+ "Group by o.productDetail.productItem.id")
	List<Object[]> statisticsFinance(String shopId);
	@Query("Select sum(o.money),sum(o.quantity) from OrderItem o where o.order.shop.id=?1")
	List<Object[]> statisticsQuanity(String shopId);
}
