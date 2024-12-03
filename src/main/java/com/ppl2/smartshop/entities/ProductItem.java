package com.ppl2.smartshop.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ppl2.smartshop.entities.datatypes.constrains.ScopeVoucher;

@Entity
public class ProductItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(length = 1000)
	private String description;
	private Integer salePrice;
	private Integer originalPrice;
	private Integer numPurchases=0;
	private Float rating=0.0f;
	private String image;
	private String optionsName;
	@OneToMany(mappedBy = "productItem",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<ProductDetail> productDetails=new HashSet<>();
	
	@OneToMany(mappedBy = "productItem",fetch = FetchType.LAZY)
	private List<ProductOption> productOptions;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Shop shop;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id",nullable = false)
	private ProductTag tag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id",nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "productItem",fetch = FetchType.LAZY)
	private List<Rate> rates;
	
	@Enumerated(EnumType.STRING)
	private ScopeVoucher scope;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getNumPurchases() {
		return numPurchases;
	}

	public void setNumPurchases(Integer numPurchases) {
		this.numPurchases = numPurchases;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Set<ProductDetail> productDetails) {
		this.productDetails=productDetails;
	}

	public List<ProductOption> getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(List<ProductOption> productOptions) {
		this.productOptions = productOptions;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public ProductTag getTag() {
		return tag;
	}

	public void setTag(ProductTag tag) {
		this.tag = tag;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public ScopeVoucher getScope() {
		return scope;
	}

	public void setScope(ScopeVoucher scope) {
		this.scope = scope;
	}

	public String getOptionsName() {
		return optionsName;
	}

	public void setOptionsName(String optionsName) {
		this.optionsName = optionsName;
	}
	


	
	
}
