package com.ppl2.smartshop.security.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.ResetPasswordToken;
import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.security.repo.IResetPasswordTokenRepository;
import com.ppl2.smartshop.security.repo.IUserRepository;

import net.bytebuddy.utility.RandomString;
@Service
public class ResetPasswordTokenService implements IResetPasswordTokenService{
	
	@Autowired 
	private IResetPasswordTokenRepository resetPasswordTokenRepository;
	
	@Autowired
	private IUserRepository userRepository;
	@Override
	public ResetPasswordToken generateResetPasswordToken(String username) throws AccountNotFoundException {
		//Kiểm tra email có tồn tại hay chưa
		User user = userRepository.findByEmailOrPhone(username,username).orElse(null);
		if (user!= null) {
			String token = RandomString.make(30);
			ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByUser_UserId(user.getUserId()).orElse(null);
			if(resetPasswordToken==null) {
				resetPasswordToken=new ResetPasswordToken(token, user);
			}else {
				Long id=resetPasswordToken.getId();
				resetPasswordToken=new ResetPasswordToken(token, user);
				resetPasswordToken.setId(id);
			}
			return resetPasswordTokenRepository.save(resetPasswordToken);
		} else {
			throw new AccountNotFoundException("Could not find any user has " + username);
		}
	}

	@Override
	public ResetPasswordToken getByToken(String token) {
		return resetPasswordTokenRepository.findByToken(token);
	}

	@Override
	public void remove(Long id) {
		resetPasswordTokenRepository.deleteById(id);
		
	}
}
