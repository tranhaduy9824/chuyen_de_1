package com.ppl2.smartshop.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.services.IGeneralService;

public interface IUserService extends IGeneralService<User,Long>, UserDetailsService {
    User findByUsername(String username);
    void changePassword(Long id, String newPass);
	Page<User> getPageUser(Pageable page);
    User getByResetPasswordToken(String token);
    boolean updateLock(boolean locked);

	boolean changePassword(Long id, String oldPass, String newPass);
    
}
