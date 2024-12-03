package com.ppl2.smartshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IOrderService;
import com.ppl2.smartshop.services.IShopService;

@Controller
public class OrderShopController {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IShopService shopService;
	
	@GetMapping("/shop/order/{id}")
	public String dashboard(Model model,@PathVariable("id") String id) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ShopDTO shop = shopService.getByUser(user.getId());
		//Default Primary Secondary Success Info Warning Danger Dark
		String shopId=shop.getId();
		model.addAttribute("order",orderService.findByShop(id, shopId));
		return "administrator/order_detail";
	}
	@PostMapping("/shop/order/hold")
	public String holdOrder(Model model,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ShopDTO shop = shopService.getByUser(user.getId());
		String shopId=shop.getId();
		orderService.updateStatusOrderByShop(OrderStatus.HOLD, orderId, shopId);

		model.addAttribute("order",orderService.findByShop(orderId, shopId));
		return "administrator/order_detail";
	}
	@PostMapping("/shop/order/cancel")
	public String cancelOrder(Model model,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ShopDTO shop = shopService.getByUser(user.getId());
		String shopId=shop.getId();
		orderService.updateStatusOrderByShop(OrderStatus.CANCEL, orderId, shopId);

		model.addAttribute("order",orderService.findByShop(orderId, shopId));
		return "administrator/order_detail";
	}
}
