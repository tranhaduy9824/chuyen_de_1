package com.ppl2.smartshop.model;

import java.util.Date;
import java.util.List;

import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.entities.datatypes.constrains.Payment;

public class OrderDTO {
	private String id;

	private Date created;

	private OrderStatus status;

	private CustomerDTO customer;
	private AddressDTO address;
	private List<OrderItemDTO> items;
	private Long total;
	private Payment payment;
	private String shopAvatar;
	private String shopId;
	private String shopName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	public List<OrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public String getShopAvatar() {
		return shopAvatar;
	}
	public void setShopAvatar(String shopAvatar) {
		this.shopAvatar = shopAvatar;
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
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	
}
