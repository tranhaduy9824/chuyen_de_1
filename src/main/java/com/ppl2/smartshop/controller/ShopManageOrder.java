package com.ppl2.smartshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IOrderService;
import com.ppl2.smartshop.services.ShopService;

@Controller
public class ShopManageOrder {
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ShopService shopService;
	
	@GetMapping("/shop/orders")
	private String history(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "status",defaultValue ="NEW,HOLD,SHIPPING,DELIVERED,CANCEL,REVIEW,RETURNS,COMPLETED") List<OrderStatus> status) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		
		model.addAttribute("orders", orderService.getAllOrderByStatusAndShop(status,shop.getId()));
		model.addAttribute("status", status);
		return "/administrator/manage_order-shop";
	}
	@PostMapping("/shop/orders/cancel")
	private String cancelOrder(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		orderService.updateStatusOrderAd(OrderStatus.CANCEL, orderId);
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatusAndShop(status,shop.getId()));
		model.addAttribute("status", status);
		return "redirect:/shop/orders";
	}
	@PostMapping("/shop/orders/hold")
	private String returnOrder(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		orderService.updateStatusOrderAd(OrderStatus.HOLD, orderId);
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatusAndShop(status,shop.getId()));
		model.addAttribute("status", status);
		return "redirect:/shop/orders";
	}
}
