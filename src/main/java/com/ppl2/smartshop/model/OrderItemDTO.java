package com.ppl2.smartshop.model;

import java.util.List;

public class OrderItemDTO {
	private Long id; 
	private Integer quantity;
	private int money;
	private boolean rated;
	private String image;
	private long productItemId;
	private String title;
	private List<OptionDTO> options;
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
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public long getProductItemId() {
		return productItemId;
	}
	public void setProductItemId(long productItemId) {
		this.productItemId = productItemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<OptionDTO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
	public boolean isRated() {
		return rated;
	}
	public void setRated(boolean rated) {
		this.rated = rated;
	}
	
 	
}
