package com.ppl2.smartshop.entities;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Timestamp createdDate;
	
	@OneToOne
	@JoinColumn(nullable = false,name = "customer_id",referencedColumnName = "id")
	private Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "cart")
	private Set<LineItem> lineItems;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems.clear();
		this.lineItems.addAll(lineItems);
	}

	
}
