package com.ppl2.smartshop.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrderItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shop_id",nullable = false)
	private OrderShop order;
	
	private boolean rated=false;
	private Integer quantity;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "item_id",nullable = false)
	private ProductDetail productDetail;
	
	private int money;
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public OrderShop getOrder() {
		return order;
	}
	public void setOrder(OrderShop order) {
		this.order = order;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ProductDetail getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}
	public boolean isRated() {
		return rated;
	}
	public void setRated(boolean rated) {
		this.rated = rated;
	}
	
}
