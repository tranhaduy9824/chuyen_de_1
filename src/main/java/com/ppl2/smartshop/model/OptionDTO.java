package com.ppl2.smartshop.model;

import java.io.Serializable;

public class OptionDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String feature;
	private String name;
	private Long productItemId;
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductItemId() {
		return productItemId;
	}
	public void setProductItemId(Long productItemId) {
		this.productItemId = productItemId;
	}
	@Override
	public String toString() {
		return "OptionDTO [id=" + id + ", feature=" + feature + ", name=" + name + ", productItemId=" + productItemId
				+ "]";
	}
	
}
