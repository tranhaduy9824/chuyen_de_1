package com.ppl2.smartshop.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.entities.datatypes.constrains.Payment;

@Entity
public class OrderShop {
	@Id
    @GeneratedValue(generator = "order-generator")
    @GenericGenerator(name = "order-generator", 
    parameters=@Parameter(name = "prefix", value = "DH"), 
    strategy = "com.ppl2.smartshop.until.MyGeneratorId") 
	private String id;
	
	private Date created;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id",nullable = false,referencedColumnName = "id")
	private Customer customer;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	private List<OrderItem> items;
	
	@Enumerated(EnumType.STRING)
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "address_id",nullable = false)
	private Address address;
	
	@ManyToOne
	@JoinColumn(name = "shop_id",nullable = false)
	private Shop shop;
	
//	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST})
//    @JoinTable(name = "order_voucherapp", 
//      joinColumns = 
//        { @JoinColumn(name = "order_id", referencedColumnName = "id") },
//      inverseJoinColumns = 
//        { @JoinColumn(name = "voucher_app", referencedColumnName = "id") })
//	private VoucherApp voucherApp;
//	
//	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.PERSIST})
//    @JoinTable(name = "order_vouchershop", 
//      joinColumns = 
//        { @JoinColumn(name = "order_id", referencedColumnName = "id") },
//      inverseJoinColumns = 
//        { @JoinColumn(name = "voucher_shop", referencedColumnName = "id") })
//	private VoucherShop voucherShop;
	
	
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
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
//	public VoucherApp getVoucherApp() {
//		return voucherApp;
//	}
//	public void setVoucherApp(VoucherApp voucherApp) {
//		this.voucherApp = voucherApp;
//	}
//	public VoucherShop getVoucherShop() {
//		return voucherShop;
//	}
//	public void setVoucherShop(VoucherShop voucherShop) {
//		this.voucherShop = voucherShop;
//	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
