package com.ppl2.smartshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.config.CartBean;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.services.ICategoryService;
import com.ppl2.smartshop.services.IProductCartService;
import com.ppl2.smartshop.entities.Rate;
import com.ppl2.smartshop.model.RateDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.IProductItemService;
import com.ppl2.smartshop.services.IRateService;


@Controller
public class ProductDetailPage {
	@Autowired
	private IProductItemService productServices;
	@Autowired
	private IProductCartService cartService;
	@Autowired
	private IRateService rateService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private CartBean cartBean;

	@PostMapping("/product-detail/{id}")
	private String updateCart(@RequestParam Long detailId, @PathVariable long id,
			@RequestParam(defaultValue = "1", required = false) Integer quantity, Model model, HttpSession session) {

		cartService.addItem(detailId, quantity);
		session.setAttribute("sizeCart", cartBean.getCartItems().size());
		ProductDTO p = productServices.findById(id);
		model.addAttribute("product", p);
		return "shop-item";

	}

	@GetMapping("/product-detail/{id}")
	private String product_detail(HttpServletRequest request, @PathVariable long id,
			@RequestParam(name = "orderId", required = false) String orderId, Model model) {
		ProductDTO p = productServices.findById(id);
		request.getSession().setAttribute("details", p.getDetailList());
		if(orderId==null) {
			
		}
		List<Rate> ratingList = rateService.findByProductId(id);
		model.addAttribute("orderId",orderId);
		model.addAttribute("ratingList", ratingList);
		model.addAttribute("product", p);
		model.addAttribute("categoryList",categoryService.classify());
		return "shop-item";
	}

	@PostMapping("/rating/product/{id}")
	public String submitReview(HttpServletRequest request, @PathVariable long id,@RequestParam(name="orderId",required = true) Long orderId,
			@RequestParam("comment") String comment, @RequestParam("point") Float point, Model model) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		RateDTO rating = new RateDTO();
		rating.setComment(comment);
		rating.setPoint(point);
		rating.setUserId(user.getId());
		rating.setProductId(id);
		
		rateService.save(orderId,rating);

		return "redirect:/product-detail/" + id;
	}

}
