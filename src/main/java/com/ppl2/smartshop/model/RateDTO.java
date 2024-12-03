package com.ppl2.smartshop.model;

import java.util.Date;

public class RateDTO {
	private Long id;
	private Float point;
	private String comment;
	private Date created;
	private Long userId;
	private Long productId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	
}
