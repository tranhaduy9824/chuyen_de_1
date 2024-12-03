package com.ppl2.smartshop.entities;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = true, length = 15, unique = true)
    private String phone;
    private String fullname;
    @Column(nullable = true,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String avatar;
    private boolean locked=false;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

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

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", phone=" + phone + ", fullname=" + fullname + ", email=" + email
				+ ", password=" + password + ", avatar=" + avatar + ", locked=" + locked + ", roles=" + roles + "]";
	}
    
}