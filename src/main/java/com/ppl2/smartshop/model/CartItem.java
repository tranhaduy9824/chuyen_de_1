package com.ppl2.smartshop.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.ppl2.smartshop.entities.datatypes.constrains.ScopeVoucher;

public class CartItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long detailId;
	private Integer quantity;
	private Integer salePrice;
	private String image;
	private String title;
	private Integer price;
	private Integer shippingCost;
	private ScopeVoucher scope;
	private List<OptionDTO> options;
	private Integer quantityItems;
	
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getQuantityItems() {
		return quantityItems;
	}
	public void setQuantityItems(Integer quantityItems) {
		this.quantityItems = quantityItems;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer salePrice) {
		this.price = salePrice;

	}
	public ScopeVoucher getScope() {
		return scope;
	}
	public void setScope(ScopeVoucher scope) {
		this.scope = scope;
	}

	public List<OptionDTO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}

	public CartItem() {
		detailId=0L;
		quantityItems=0;
	}
	public CartItem(Long detailId, Integer quantity) {
		this.detailId = detailId;
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(detailId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(detailId, other.detailId);
	}
	public Integer getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(Integer shippingCost) {
		this.shippingCost = shippingCost;
	}

	
}
