package com.ppl2.smartshop.model;


import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.Mode;

public class AddressDTO {
	private Long id;
	private String address;
	private City city;
	private Mode mode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public boolean isEmpty() {
		return id==null && address==null && city==null; 
	}
	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", address=" + address + ", city=" + city + ", mode=" + mode + "]";
	}
	
}
