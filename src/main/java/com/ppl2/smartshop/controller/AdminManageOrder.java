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

import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IOrderService;

@Controller
public class AdminManageOrder {
	@Autowired
	private IOrderService orderService;

	@GetMapping("/admin/orders")
	public String adminManage(Model model,
			@RequestParam(name = "status", defaultValue = "NEW,HOLD,SHIPPING,DELIVERED,CANCEL,REVIEW,RETURNS,COMPLETED") List<OrderStatus> status) {
		model.addAttribute("orders", orderService.getAllOrderByStatus(status));
		model.addAttribute("status", status);
		return "administrator/manage_order-admin";
	}



	@PostMapping("/admin/orders/cancel")
	private String cancelOrder(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String orderId) {
		// UserPrinciple user = (UserPrinciple)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrderAd(OrderStatus.CANCEL, orderId);
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status));
		model.addAttribute("status", status);
		return "redirect:/admin/orders";
	}

	@PostMapping("/admin/orders/new")
	private String newOrder(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrder(OrderStatus.NEW, orderId, user.getId());
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status));
		model.addAttribute("status", status);
		return "redirect:/admin/orders";
	}

	@PostMapping("/admin/orders/shipping")
	private String deliveredOrder(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String orderId) {
		// UserPrinciple user = (UserPrinciple)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrderAd(OrderStatus.SHIPPING, orderId);
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status));
		model.addAttribute("status", status);
		return "redirect:/admin/orders";
	}

	@PostMapping("/admin/orders/delivered")
	private String shippingOrder(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String orderId) {
		// UserPrinciple user = (UserPrinciple)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrderAd(OrderStatus.DELIVERED, orderId);
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status));
		model.addAttribute("status", status);
		return "redirect:/admin/orders";
	}
}
