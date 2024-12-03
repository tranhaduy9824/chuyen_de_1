package com.ppl2.smartshop.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FeeOption {
	@Id
	private String name;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	

}
