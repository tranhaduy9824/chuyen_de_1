package com.ppl2.smartshop.model;

import com.ppl2.smartshop.entities.User;

public class ShopDTO {
	private String id;
	private String name;
	private String avatar;
	private String address;
	private String position;
	private User user;
	private Long userId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public ShopDTO(String id, String name, String avatar, String address, String position, User user, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.address = address;
		this.position = position;
		this.userId = userId;
	}
	public ShopDTO() {
	}
	
}
