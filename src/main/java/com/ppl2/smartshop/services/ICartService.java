package com.ppl2.smartshop.services;

import com.ppl2.smartshop.model.CartItem;

public interface ICartService {
	public void addItem(CartItem item);
	public void removeItem(long id);
	public void updateItem(CartItem item);
	
}
