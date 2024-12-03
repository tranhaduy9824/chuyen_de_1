package com.ppl2.smartshop.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppl2.smartshop.entities.ResetPasswordToken;


@Repository
public interface IResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken , Long> {
	ResetPasswordToken findByToken(String token);
	Optional<ResetPasswordToken> findByUser_UserId(Long userId);
}
