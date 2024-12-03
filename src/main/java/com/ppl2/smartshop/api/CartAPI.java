package com.ppl2.smartshop.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ppl2.smartshop.services.IProductCartService;


@RestController
@RequestMapping("/api/cart")
public class CartAPI {
	@Autowired
	private IProductCartService cartService;

	@PutMapping("/update-quantity")
	private ResponseEntity<?>  updateQuantity(HttpServletResponse response,
			@RequestParam Long productDetailId,
			@RequestParam(defaultValue = "1",required = false) Integer quantity, 
			Model model ){
		try {
			
			cartService.updateQuantityCartItem(productDetailId, quantity);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	@DeleteMapping("/delete-item")
	private ResponseEntity<?>  deleteItem(HttpServletResponse response,
			@RequestParam Long productDetailId, 
			Model model ){
		try {
			
			cartService.removeItem(productDetailId);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
