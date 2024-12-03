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
public class OrderHistoryController {
	@Autowired
	private IOrderService orderService;
	@GetMapping("/order/history")
	private String history(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "status",defaultValue ="NEW,HOLD,SHIPPING,DELIVERED,CANCEL,REVIEW,RETURNS,COMPLETED") List<OrderStatus> status) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("orders", orderService.getAllOrderByStatus(status,user.getId()));
		model.addAttribute("status", status);
		return "order-history";
	}
	@PostMapping("/order/comfirm")
	private String comfirmOrder(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrder(OrderStatus.REVIEW, orderId, user.getId());
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status,user.getId()));
		model.addAttribute("status", status);
		return "order-history";
	}
	@PostMapping("/order/cancel")
	private String cancelOrder(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrder(OrderStatus.CANCEL, orderId, user.getId());
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status,user.getId()));
		model.addAttribute("status", status);
		return "order-history";
	}
	@PostMapping("/order/return")
	private String returnOrder(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam String orderId) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		orderService.updateStatusOrder(OrderStatus.RETURNS, orderId, user.getId());
		List<OrderStatus> status = List.of(OrderStatus.values());
		model.addAttribute("orders", orderService.getAllOrderByStatus(status,user.getId()));
		model.addAttribute("status", status);
		return "order-history";
	}
}
