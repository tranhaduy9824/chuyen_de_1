package com.ppl2.smartshop.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.ppl2.smartshop.model.CartItem;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IProductCartService;
@Component
@SessionScope
public class CartBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4481362384173619445L;
	@Autowired
	private transient IProductCartService cartService;
	@Autowired
	private HttpServletRequest request;
	private Long id;
	private Date createdTime;
	private Set<CartItem> cartItems;
	public CartBean() {
		this.cartItems=new HashSet<CartItem>();
	}
	@PostConstruct
	public void init() {
		System.out.println("Init Cart");
		try {
			UserPrinciple user = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			this.cartItems=new HashSet<CartItem>();
			cartService.getCart(user.getId(),this);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@PreDestroy
	public void destroy() {
		System.out.println("Destroy cart");
		HttpSession session = request.getSession();
		session.removeAttribute("cartBean");
		save();
	}
	public void save() {
		try {
			UserPrinciple user = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			cartService.save(id,user.getId(),cartItems);
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	@Override
	public String toString() {
		return cartItems.stream().map(CartItem::toString)
				    .collect(Collectors.joining("@"));
	}
	public Set<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
}
