package com.ppl2.smartshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppl2.smartshop.entities.Cart;

public interface ICartRepo extends JpaRepository<Cart, Long>{
	@Query("Select crt from Cart crt where crt.customer.id="
			+ " (Select c.id from Customer c where c.user.id=:userId) ")
	Cart getCart(@Param("userId") Long userId);
}
