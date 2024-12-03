package com.ppl2.smartshop.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.ScopeVoucher;

public class ProductDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3791965781162221049L;
	private Long id;
	private String title;
	private String description;
	private Integer salePrice;
	private Integer originalPrice;
	private Integer numPurchases;
	private String image;
	private List<OptionDTO> options;
	private List<ProductDetailDTO> DetailList=new LinkedList<ProductDetailDTO>();
	private String shopId;
	private String shopName;
	private String shopAvatar;
	private City position;
	private Float rating;
	private Long tagId;
	private Integer categoryId;
	private String category;
	private String address;
	private List<RateDTO> rateList;
	private ScopeVoucher scope;
	private List<String> optionsName=new LinkedList<>();
	private String descriptionElements;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<OptionDTO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
	public List<ProductDetailDTO> getDetailList() {
		return DetailList;
	}
	public void setDetailList(List<ProductDetailDTO> detailList) {
		DetailList = detailList;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAvatar() {
		return shopAvatar;
	}
	public void setShopAvatar(String shopAvatar) {
		this.shopAvatar = shopAvatar;
	}
	public City getPosition() {
		return position;
	}
	public void setPosition(City position) {
		this.position = position;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<RateDTO> getRateList() {
		return rateList;
	}
	public void setRateList(List<RateDTO> rateList) {
		this.rateList = rateList;
	}
	public ScopeVoucher getScope() {
		return scope;
	}
	public void setScope(ScopeVoucher scope) {
		this.scope = scope;
	}
	public String getDescriptionElements() {
		return descriptionElements;
	}
	public void setDescriptionElements(String descriptionElements) {
		this.descriptionElements = descriptionElements;
	}
	public List<String> getOptionsName() {
		return optionsName;
	}
	public void setOptionsName(List<String> optionsName) {
		this.optionsName = optionsName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
}
