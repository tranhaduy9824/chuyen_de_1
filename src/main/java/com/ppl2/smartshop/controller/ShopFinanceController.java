package com.ppl2.smartshop.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IProductItemService;
import com.ppl2.smartshop.services.ShopService;

@Controller
public class ShopFinanceController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private IProductItemService productItemService;
	@GetMapping("/shop/finance")
	private String history(Model model) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		Map<String, Long> map=productItemService.statisticsSum(shop.getId());
		model.addAttribute("total",map.get("total"));
		model.addAttribute("quantity",map.get("quantity"));
		model.addAttribute("products", productItemService.statisticsProductByShop(shop.getId()));
		return "/administrator/shop_finance";
	}
}
