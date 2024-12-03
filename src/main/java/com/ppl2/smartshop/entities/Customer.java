package com.ppl2.smartshop.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ppl2.smartshop.entities.datatypes.constrains.Gender;
@Entity
public class Customer {
	@Id
    @GeneratedValue(generator = "customer-generator")
    @GenericGenerator(name = "customer-generator", 
    parameters=@Parameter(name = "prefix", value = "KH"), 
    strategy = "com.ppl2.smartshop.until.MyGeneratorId")
	private String id;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private LocalDate birthday;
	
	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses.clear();
		if(addresses!=null) {
			this.addresses.addAll(addresses);
		}
		
	}

	
}
