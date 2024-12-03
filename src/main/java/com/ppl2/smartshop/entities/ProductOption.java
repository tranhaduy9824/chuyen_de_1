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
public class ProductOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "product_item_id" ,nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ProductItem productItem;
	private String feature;
	
	private String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public ProductItem getProductItem() {
		return productItem;
	}
	public void setProductItem(ProductItem productItem) {
		this.productItem = productItem;
	}
	@Override
	public String toString() {
		return "ProductOption [id=" + id + ", productItem=" + productItem + ", feature=" + feature + ", name=" + name
				+ "]";
	}

	
}
