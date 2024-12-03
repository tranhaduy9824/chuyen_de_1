package com.ppl2.smartshop.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Float point;
	private String comment;
	private Date created;
	@ManyToOne
	@JoinColumn(name = "customer_id",nullable = false,referencedColumnName = "id")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "product_item_id",nullable = false)
	private ProductItem productItem;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Float getPoint() {
		return point;
	}
	public void setPoint(Float point) {
		this.point = point;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public ProductItem getProductItem() {
		return productItem;
	}
	public void setProductItem(ProductItem productItem) {
		this.productItem = productItem;
	}

	
}
