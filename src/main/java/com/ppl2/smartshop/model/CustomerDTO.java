package com.ppl2.smartshop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ppl2.smartshop.entities.datatypes.constrains.Gender;

public class CustomerDTO {
	private String id;
	private Gender gender;
	private LocalDate birthday;
    private Long userId;
    private String phone;
    private String fullname;
    private String email;
    private String avatar;
	private List<AddressDTO> addresses=new ArrayList<AddressDTO>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Gender getGender() {
		return gender;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<AddressDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", gender=" + gender + ", birthday=" + birthday + ", userId=" + userId
				+ ", phone=" + phone + ", fullname=" + fullname + ", email=" + email + ", avatar=" + avatar
				+ ", addresses=" + addresses + "]";
	}
	
	
}
