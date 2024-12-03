package com.ppl2.smartshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.ScopeVoucher;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.services.ICategoryService;
import com.ppl2.smartshop.services.IProductItemService;

@Controller
public class ProductListPage {
	@Autowired
	private IProductItemService productItemService;
	@Autowired
	private ICategoryService categoryService;
	@GetMapping("/product-list")
	private String productListPage(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "12") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "UNSORTED") String sort,
			@RequestParam(name = "categoryId",required = false) Integer categoryId,
			@RequestParam(name = "maxPrice",required = false) Integer maxPrice,
			@RequestParam(name = "minPrice",required = false) Integer minPrice,
			@RequestParam(name = "scope",required = false) List<ScopeVoucher> scope,
			@RequestParam(name = "city",required = false) City city
			) {
		Pageable pagination;
		
		if(!sort.equals("UNSORTED")) {
			String[] typeSort=sort.split(": ");
			if(typeSort[1].equals("ASC"))
				pagination=PageRequest.of(page-1, size,Sort.by(typeSort[0]).ascending());
			else
				pagination=PageRequest.of(page-1, size,Sort.by(typeSort[0]).descending());
			
		}else {
			pagination=PageRequest.of(page-1, size);
		}
		ScopeVoucher scopeVoucher=null;
		if(scope!=null) {
			if(scope.contains(ScopeVoucher.SHIP) && scope.contains(ScopeVoucher.SHOP)) {
				scopeVoucher=ScopeVoucher.ALL;
			}else {
				scopeVoucher=scope.get(0);
			}
		}

		
		Page<ProductDTO> pageProduct=productItemService.filter(city, scopeVoucher, categoryId, maxPrice, minPrice, pagination);
		model.addAttribute("productList",pageProduct.get().toList());
		model.addAttribute("cities", City.values());
		model.addAttribute("scopes",ScopeVoucher.values());
		model.addAttribute("page",pageProduct);
		model.addAttribute("scope",scope);
		model.addAttribute("city",city);
		model.addAttribute("minPrice",minPrice);
		model.addAttribute("maxPrice",maxPrice);
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("params",("?"+request.getQueryString()).replaceAll("(\\?|&)" + "page" + "=[^&]*", ""));
		model.addAttribute("categoryList",categoryService.classify());
		return "shop-product-list";
	}
}
