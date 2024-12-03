package com.ppl2.smartshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IOrderService;
import com.ppl2.smartshop.services.IProductItemService;
import com.ppl2.smartshop.services.IRateService;
import com.ppl2.smartshop.services.IShopService;

@Controller
public class AdminDashBoardController {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IShopService shopService;
	@Autowired
	private IRateService rateService;
	@Autowired
	private IProductItemService productItemService;
	@GetMapping("/admin/home")
	public String dashboard(Model model) {

		Map<OrderStatus, Long> map=orderService.statisticsAll();
		List<String[]> results=new ArrayList<>();
        for (Entry<OrderStatus, Long> entry : map.entrySet()) {
            results.add(new String[] {entry.getKey().getName(),entry.getValue().toString(),color(entry.getKey())});
        }
		model.addAttribute("ordersStatus",results);
//		model.addAttribute("numRates",rateService.statisticsRateByShop(shopId));
//		model.addAttribute("orders",orderService.getOrderNeedSolvingByShop(shopId));
//		model.addAttribute("numProducts",productItemService.countByShop(shopId));
		return "administrator/admin_dashboard";
	}
	private String color(OrderStatus status) {
		switch (status) {
		case NEW : {
			return "primary";
		}
		case HOLD:{
			return "info";
		}
		case RETURNS:{
			return "secondary";
		}
		case REVIEW:{
			return "warning";
		}
		case DELIVERED:{
			return "success";
		}
		case SHIPPING:{
			return "default";
		}
		case CANCEL:{
			return "danger";
		}
		case COMPLETED:{
			return "success";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + status);
		}
	}
}
