package com.ppl2.smartshop.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppl2.smartshop.entities.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String name);
}
