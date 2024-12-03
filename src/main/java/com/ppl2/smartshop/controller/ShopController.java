package com.ppl2.smartshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.ProductItem;
import com.ppl2.smartshop.entities.ProductTag;
import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.ProductItemService;
import com.ppl2.smartshop.services.ProductTagService;
import com.ppl2.smartshop.services.ShopService;



@Controller
public class ShopController {
	
	@Autowired
	private ProductTagService productTagsService;
	
	@Autowired
	private ProductItemService productItemService;
	@Autowired
	private ShopService shopService;
	
	@GetMapping("/shop-detail/{id}")
	private String profilePage(Model model,HttpServletRequest request,HttpServletResponse response,
	                           @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
	                           @RequestParam(name = "size", required = false, defaultValue = "4") Integer size,
	                           @PathVariable("id") String shopId,
	                           @RequestParam(name = "tagId", required = false) Long tagId) {
	    Page<ProductItem> productItems;
	    List<ProductTag> productTags = productTagsService.findProductTagByShopId(shopId);
	    Shop shop = shopService.findById(shopId);
	    if (tagId != null) {
	        // Perform pagination for findAllByTag
	        Pageable pageable = PageRequest.of(page - 1, size);
	        
	        productItems = productItemService.findAllByTag(tagId, pageable);

	    } else {
	        // Perform pagination for findAllByShop
	        Pageable pageable = PageRequest.of(page - 1, size);
	       
	        productItems = productItemService.findAllByShop(shop, pageable);

	    }
        
        // Add pagination attributes to the model
        
	    model.addAttribute("shopId",shopId);
	    model.addAttribute("tagId",tagId);
	    try {
		    model.addAttribute("tagName",tagId!=null ? productItems.getContent().get(0).getTag().getTagName(): "Sản phẩm" );
		} catch (Exception e) {
		    model.addAttribute("tagName","Sản phẩm" );
		}
	    model.addAttribute(shop);
	    model.addAttribute("size",size);
	    model.addAttribute("topProducts",shopService.getTopProductOfShop(shopId));
	    model.addAttribute("page",productItems);
	    model.addAttribute("productList", productItems.toList());
	    model.addAttribute("productTags", productTags);

	    return "shop-page";
	}


}
