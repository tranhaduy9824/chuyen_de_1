package com.ppl2.smartshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.model.ProductDetailDTO;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IProductDetailService;
import com.ppl2.smartshop.services.IProductItemService;
import com.ppl2.smartshop.services.IShopService;

@Controller
public class ProductStoreController {
	@Autowired
	private IProductDetailService productDetailService;
	@Autowired
	private IProductItemService productItemService;
	@Autowired
	private IShopService shopService;

	@GetMapping("/shop/store-product")
	public String listAll(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "keyword", required = false) String keyword) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ShopDTO shop = shopService.getByUser(user.getId());
		Pageable pageable=PageRequest.of(page-1, size);
		if(keyword==null) {
			model.addAttribute("page", productDetailService.getAllByShop(shop.getId(), pageable));
		}else {
			model.addAttribute("page", productDetailService.searchByShop(shop.getId(),keyword, pageable));
			model.addAttribute("keyword",keyword);
		}
		model.addAttribute("pageable",pageable);
		return "administrator/manage_store";
	}

	@GetMapping("/shop/store-product/{id}")
	public String listProductInStore(Model model, @PathVariable(name = "id") Long productId) {
		model.addAttribute("product", productItemService.findById(productId));
		model.addAttribute("details",new ArrayList<>(5));
		return "administrator/manage_store_product_detail";
	}

	@PostMapping("/shop/store-product")
	public String listProductInStore(Model model, @ModelAttribute ProductDetailDTO detailDTO) {
		productDetailService.update(detailDTO);
		return "redirect:/shop/store-product";
	}
	@PostMapping("/shop/store-product/{id}")
	public String listProductInStore(Model model,@PathVariable(name = "id") Long productId,@ModelAttribute("details") ArrayList<ProductDetailDTO> details) {
		details.stream().map(detail->detail.getOptions() ).forEach(System.out::println);
		System.out.println(details.size());
		productDetailService.saveAll(details);
		model.addAttribute("product", productItemService.findById(productId));
		return "administrator/manage_store_product_detail";
	}
}
