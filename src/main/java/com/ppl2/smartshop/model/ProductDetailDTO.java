package com.ppl2.smartshop.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ProductDetailDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -651598406302839748L;
	private Long id;
	private Integer quantity=0;
	private Float weight=0f;
	private Float size=0f;
	private String image;
	private String title;
	private List<OptionDTO> options=new LinkedList<OptionDTO>();

	public List<OptionDTO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Float getSize() {
		return size;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	

	
}
