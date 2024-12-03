package com.ppl2.smartshop.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ppl2.smartshop.entities.datatypes.constrains.City;

@Entity
public class Shop {

	@Id
    @GeneratedValue(generator = "shop-generator")
    @GenericGenerator(name = "shop-generator", 
    parameters=@Parameter(name = "prefix", value = "CH"), 
    strategy = "com.ppl2.smartshop.until.MyGeneratorId")
	private String id;
	private String name;
	private String address;
	private String avatar;
	
	@Enumerated(EnumType.STRING)
	private City position;
	
	@OneToOne
	@JoinColumn(nullable = false,name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "shop")
	private List<ProductTag> tags;
	
	@OneToMany(mappedBy = "shop")
	private List<ProductItem> productItems;
	
//	@OneToMany(mappedBy = "shop",cascade = CascadeType.ALL)
//	private List<VoucherShop> vouchers;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public City getPosition() {
		return position;
	}
	public void setPosition(City position) {
		this.position = position;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<ProductTag> getTags() {
		return tags;
	}
	public void setTags(List<ProductTag> tags) {
		this.tags = tags;
	}
	public List<ProductItem> getProductItems() {
		return productItems;
	}
	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}
//	public List<VoucherShop> getVouchers() {
//		return vouchers;
//	}
//	public void setVouchers(List<VoucherShop> vouchers) {
//		this.vouchers = vouchers;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
