package com.ppl2.smartshop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.Category;
import com.ppl2.smartshop.entities.ProductItem;
import com.ppl2.smartshop.entities.ProductTag;
import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.CategoryService;
import com.ppl2.smartshop.services.ProductItemService;
import com.ppl2.smartshop.services.ProductTagService;
import com.ppl2.smartshop.services.ShopService;

@Controller
public class ShopManagerController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductTagService productTagsService;
	@Autowired
	private ProductItemService productItemService;
	@Autowired
	private ShopService shopService;


	// Manage Tags of Product
	@GetMapping("/shop/manageTags")
	private String shopManageTagspage(Model model) {

		// List<ProductTag> productTags = productTagRepository.findAll();
		model.addAttribute("categoryList", categoryService.classify());

		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Shop shop = shopService.getShopById("CH3");
		Shop shop1 = shopService.getShopByUserId(user.getId());
		List<ProductTag> productTags1 = productTagsService.findProductTagByShopId(shop1.getId());
		// List<ProductTag> productTags =
		// productTagsService.findProductTagByShopId("CH3");
		model.addAttribute("productTags", productTags1);
		return "/administrator/manage_product-tag";
	}

	@GetMapping("/shop/manageTags/form")
	public String showTagsForm(Model model) {
		ProductTag t = new ProductTag();
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		// System.out.println(shop.toString());
		t.setShop(shop);
		model.addAttribute("productTag", t);
		return "/administrator/forms-input-product-tag";
	}

	@GetMapping("/shop/manageTags/form/{id}")
	public String showTagsForm(@PathVariable("id") Long id, Model model) {
		ProductTag productTag = productTagsService.getProductById(id);
		model.addAttribute("productTag", productTag);
		return "/administrator/forms-input-product-tag";
	}

	@PostMapping("/shop/manageTags/add")
	public String addProductTag(@RequestParam("tag") String tag) {
		ProductTag pTag = new ProductTag();
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		pTag.setShop(shop);
		pTag.setTagName(tag);
		productTagsService.saveProductTag(pTag);
		return "redirect:/shop/manageTags";
	}

	@PostMapping("/saveProductTag")
	public String updateProductTag(@ModelAttribute("tag") ProductTag productTag,
			@RequestParam("newType") String newType, @RequestParam("oldType") String oldType,
			@RequestParam("id") Long tagId) {
		// Retrieve the existing tag by ID
		ProductTag existingTag = productTagsService.getProductById(tagId);

		// Update the tag properties
		existingTag.setTagName(newType);
		// Update other properties as needed

		// Save the modified tag
		productTagsService.saveProductTag(existingTag);

		return "redirect:/shop/manageTags";
	}

	@PostMapping("/shop/manageTags/delete")
	public String deleteProductTag(@RequestParam("tagId") Long tagId) {
		productTagsService.deleteProductTagsById(tagId);
		return "redirect:/shop/manageTags";
	}

	@GetMapping("/shop/manageProductItems")
	public String getProductItems(@RequestParam(name = "searchTitle", required = false) String searchTitle,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size, Model model) {
		Page<ProductItem> productItems;
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		if (searchTitle != null && !searchTitle.isEmpty()) {
			productItems = productItemService.findAllByTitleContainingAndShop(searchTitle, shop,
					PageRequest.of(page, size));
		} else {
			productItems = productItemService.findAllByShop(shop, PageRequest.of(page, size));
		}
		model.addAttribute("shop",shop);
		model.addAttribute("size", size);
		model.addAttribute("searchTitle", searchTitle);
		model.addAttribute("productItems", productItems.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productItems.getTotalPages());
		return "/administrator/manage_product-item";
	}

	@GetMapping("shop/manageProductItems/form")
	public String showItemsForm(Model model) {
		ProductDTO t = new ProductDTO();
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop = shopService.getShopByUserId(user.getId());
		t.setShopId(shop.getId());
		t.setShopName(shop.getName());
		t.setRating(0.0f);
		List<Category> categories = categoryService.findAll();
		List<ProductTag> productTags = productTagsService.findProductTagByShopId(shop.getId());
		model.addAttribute("productTags", productTags);
		model.addAttribute("categories", categories);
		model.addAttribute("productItem", t);
		return "/administrator/forms-input-product-item";
	}

	@GetMapping("/shop/information")
	public String manageShop(Model model) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop =shopService.getShopByUserId(user.getId());

		model.addAttribute("shop", shop);
		return "/administrator/manage_shop";
	}
	
	@GetMapping("/shop/manageProductItems/form/{id}")
	public String showItemsForm(@PathVariable("id") Long id, Model model) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop =shopService.getShopByUserId(user.getId());
		List<Category> categories = categoryService.findAll();
		List<ProductTag> productTags = productTagsService.findProductTagByShopId(shop.getId());
		ProductDTO productItem = productItemService.getProductById(id);
		model.addAttribute("productTags", productTags);
		model.addAttribute("categories", categories);
		model.addAttribute("productItem", productItem);
		return "/administrator/forms-input-product-item";
	}

	@PostMapping("/add-productItem")
	public String addProductItem(@ModelAttribute("productItem") ProductDTO productItem) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop =shopService.getShopByUserId(user.getId());
		productItem.setShopId(shop.getId());
		System.out.println(productItem.getOptionsName());
		productItem= productItemService.add(productItem);
		return "redirect:/shop/manageProductItems/form/"+productItem.getId();
	}
	@PostMapping("/update-productItem")
	public String updateProductItem(@ModelAttribute("productItem") ProductDTO productItem) {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop =shopService.getShopByUserId(user.getId());
		productItem.setShopId(shop.getId());
		productItem= productItemService.save(productItem);
		return "redirect:/shop/manageProductItems/form/"+productItem.getId();
	}
	@PostMapping("/productItems/delete/{id}")
	public String deleteProductItem(@PathVariable Long id) {
		productItemService.remove(id);
		return "redirect:/shop/manageTags";
	}

	@PostMapping("/shop/manage-shop/save")
	public String updateShop(@ModelAttribute("shop") Shop shop)
			throws IOException {
		UserPrinciple user =(UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		shop.setUser(new User());
		shop.getUser().setUserId(user.getId());

		shopService.saveShop(shop);
		return "redirect:/shop/manageProductItems";

	}
	@GetMapping("/shop/manage")
	public String gotoMyShop() {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ShopDTO shop = shopService.getByUser(user.getId());
		return "redirect:/shop-detail/"+shop.getId();
	}
}
