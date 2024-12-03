package com.ppl2.smartshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.config.CartBean;
import com.ppl2.smartshop.entities.datatypes.constrains.Payment;
import com.ppl2.smartshop.model.CartProduct;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.ICustomerService;
import com.ppl2.smartshop.services.IProductCartService;

@Controller
public class CartController {
	@Autowired
	private CartBean cart;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IProductCartService cartService;

	@GetMapping("/cart")
	private String getCart(Model model) {

		model.addAttribute("cartProducts", cartService.getAllProductCart(cart.getCartItems()));
		return "shop-shopping-cart";
	}

	@PostMapping("/cart")
	private String checkout(Model model, @RequestParam List<Long> itemId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("customer", customerService.findByUserId(user.getId()));
		List<CartProduct> cartProducts = cartService.getAllProductCart(cart.getCartItems().stream()
				.filter(item -> itemId.contains(item.getDetailId())).collect(Collectors.toSet())).stream()
				.map(cartProduct -> {
					cartProduct.getCartItems().removeIf(item -> item.getQuantityItems() > item.getQuantity());
					return cartProduct;
				}).collect(Collectors.toList());
		model.addAttribute("cartProducts", cartProducts);
		Long subTotal = 0L;
		Integer shippingTotal = 0;
		for (CartProduct cartProduct : cartProducts) {
			subTotal += cartProduct.getSubTotal();
			shippingTotal += cartProduct.getShippingCost();
		}
		model.addAttribute("shippingTotal", shippingTotal);
		model.addAttribute("subTotal", subTotal);
		model.addAttribute("total", shippingTotal + subTotal);
		return "shop-checkout";

	}

	@PostMapping("/cart/checkout")
	private String order(HttpSession session, Model model, @RequestParam(name = "itemIds") List<Long> itemIds,
			@RequestParam(name = "address") Long addressId, @RequestParam(name = "CashOnDelivery") Payment payment) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cartService.checkout(addressId, payment, user.getId(), itemIds);
		session.setAttribute("sizeCart", ((Integer) session.getAttribute("sizeCart")) - itemIds.size());
		return "order-successfully";
	}

	@GetMapping("/cart/delete-item")
	private String removeItem(HttpServletResponse response, @RequestParam Long productDetailId,
			HttpServletRequest request, Model model) {
		cartService.removeItem(productDetailId);
		HttpSession session = request.getSession();
		session.setAttribute("sizeCart", cart.getCartItems().size());

		return "redirect:/cart";
	}
	@GetMapping("/cart/update-quantity")
	private String removeItem(HttpServletResponse response, @RequestParam Long productDetailId,@RequestParam Integer quantity,
			HttpServletRequest request, Model model) {
		cartService.updateQuantityCartItem(productDetailId, quantity);
		HttpSession session = request.getSession();
		session.setAttribute("sizeCart", cart.getCartItems().size());

		return "redirect:/cart";
	}
}
