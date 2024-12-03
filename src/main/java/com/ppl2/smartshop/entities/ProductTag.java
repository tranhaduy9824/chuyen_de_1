package com.ppl2.smartshop.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class ProductTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String tagName;
	@ManyToOne
	@JoinColumn(name = "shop_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Shop shop;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String name) {
		this.tagName = name;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
