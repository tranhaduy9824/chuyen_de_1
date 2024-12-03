package com.ppl2.smartshop.security.service;

import javax.security.auth.login.AccountNotFoundException;

import com.ppl2.smartshop.entities.ResetPasswordToken;

public interface IResetPasswordTokenService {
	public ResetPasswordToken generateResetPasswordToken(String username) throws AccountNotFoundException;
	public ResetPasswordToken getByToken(String token);
	public void remove(Long id);
}
