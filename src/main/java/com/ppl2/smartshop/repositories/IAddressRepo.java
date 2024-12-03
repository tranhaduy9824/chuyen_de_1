package com.ppl2.smartshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppl2.smartshop.entities.Address;

public interface IAddressRepo extends JpaRepository<Address, Long>{

}
