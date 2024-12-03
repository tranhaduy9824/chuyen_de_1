package com.ppl2.smartshop.model;

import java.util.ArrayList;
import java.util.List;

public class CartProduct {

	private String shopId;
	private String shopName;
	private String shopAvatar;
	private Integer shippingCost=0;
	private Long id;
	private Long subTotal=0L;
	private Long total=0L;

	private List<CartItem> cartItems=new ArrayList<CartItem>();
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAvatar() {
		return shopAvatar;
	}
	public void setShopAvatar(String shopAvatar) {
		this.shopAvatar = shopAvatar;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(Integer shippingCost) {
		this.shippingCost = shippingCost;
	}
	public Long getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Long subTotal) {
		this.subTotal = subTotal;
	}
	
}
