package com.ppl2.smartshop.services;

import java.util.List;
import java.util.Set;

import com.ppl2.smartshop.config.CartBean;
import com.ppl2.smartshop.entities.datatypes.constrains.Payment;
import com.ppl2.smartshop.model.CartItem;
import com.ppl2.smartshop.model.CartProduct;

public interface IProductCartService{
	List<CartProduct> getAllProductCart(Set<CartItem> cartItem);
	CartBean getCart(Long userId,CartBean cartBean);
	void save(Long id,Long userId,Set<CartItem> cartItems);
	void removeItem(Long detailId) ;
	void updateQuantityCartItem(Long productDetailId,Integer quantity);
	void addItem(Long productDetailId, Integer quantity);
	void checkout(Long addressId,Payment payment,Long userId,List<Long> itemIds);
}
