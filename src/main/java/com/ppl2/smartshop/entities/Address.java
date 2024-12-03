package com.ppl2.smartshop.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.Mode;

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String address;
	@Enumerated(EnumType.STRING)
	private City city;
	
	@Enumerated(EnumType.STRING)
	private Mode mode;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="customer_id",referencedColumnName = "id")
	private Customer customer;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
