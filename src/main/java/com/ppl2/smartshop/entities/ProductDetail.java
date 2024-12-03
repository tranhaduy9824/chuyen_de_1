package com.ppl2.smartshop.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ProductDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantity=0;
	private Float weight=0f;
	private Float size=0f;
	@ManyToOne
	@JoinColumn(name = "prouduct_item_id",nullable = false)
	private ProductItem productItem;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "details_options",
            joinColumns = {@JoinColumn(name = "product_detail_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")})
	private List<ProductOption> productOptions;
    @OneToMany(mappedBy = "productDetail",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<LineItem> lineItems;
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
	public ProductItem getProductItem() {
		return productItem;
	}
	public void setProductItem(ProductItem productItem) {
		this.productItem = productItem;
	}
	public List<ProductOption> getProductOptions() {
		return productOptions;
	}
	public void setProductOptions(List<ProductOption> productOptions) {
		this.productOptions = productOptions;
	}
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
    
    
    
}
