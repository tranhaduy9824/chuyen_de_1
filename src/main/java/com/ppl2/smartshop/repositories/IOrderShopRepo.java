package com.ppl2.smartshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ppl2.smartshop.entities.OrderShop;
import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;

public interface IOrderShopRepo extends JpaRepository<OrderShop, String>{
	List<OrderShop> findByStatusInAndCustomerId(List<OrderStatus> status,String customerId);
	Optional<OrderShop> findByIdAndCustomerUserUserId(String orderId,Long userId );
	Optional<OrderShop> findByIdAndShopId(String orderId,Long userId );
	@Query("Select o.status,count(o.id) from OrderShop o where o.shop.id=?1 Group by o.status")
	List<Object[]> countOrder(String shopId);
	@Query("Select o.status,count(o.id) from OrderShop o Group by o.status")
	List<Object[]> countOrder();
	List<OrderShop> findByStatusAndShopId(OrderStatus status,String shopId);
	Optional<OrderShop> findByIdAndShopId(String id,String shopId);

	Optional<OrderShop> findById(String orderId);
	List<OrderShop> findByShopId(String shopId);
	List<OrderShop> findByStatusInAndShopId(List<OrderStatus> status,String shopId);
	List<OrderShop> findByStatusIn(List<OrderStatus> status);
}
