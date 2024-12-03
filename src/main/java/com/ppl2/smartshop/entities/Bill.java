package com.ppl2.smartshop.entities;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
public class Bill {
	@Id
    @GeneratedValue(generator = "bill-generator")
    @GenericGenerator(name = "bill-generator", 
    parameters=@Parameter(name = "prefix", value = "HD"), 
    strategy = "com.ppl2.smartshop.until.MyGeneratorId")
	private String id;
	
	private Date issueDate;
	private Date dueDate;
	private long total;
	
	@OneToOne
	@JoinColumn(nullable = false,name = "order_id")
	private OrderShop order;
	
	@OneToOne
	@JoinColumn(nullable = false,name = "customer_id",referencedColumnName = "id")
	private Customer customer;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public OrderShop getOrder() {
		return order;
	}
	public void setOrder(OrderShop order) {
		this.order = order;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
