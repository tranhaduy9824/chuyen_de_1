package com.ppl2.smartshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.ppl2.smartshop.entities.Customer;

public interface ICustomerRepo extends JpaRepository<Customer, String>{

	@Query("SELECT c FROM Customer c WHERE c.user.userId = :userId")
	Customer findByUserUserId(@Param("userId") Long userId);
}
