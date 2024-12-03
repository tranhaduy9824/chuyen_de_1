package com.ppl2.smartshop.services;

import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.model.CustomerDTO;

public interface ICustomerService {
	public CustomerDTO updateProfile(CustomerDTO customer);
	public CustomerDTO findByUserId(Long userId);
	public void createCustomerByUser(User user);
}
