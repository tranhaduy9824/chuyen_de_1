package com.ppl2.smartshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.Category;
import com.ppl2.smartshop.entities.ProductTag;
import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.model.Message;
import com.ppl2.smartshop.model.OptionDTO;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.services.CategoryService;
import com.ppl2.smartshop.services.IProductOptionService;
import com.ppl2.smartshop.services.ProductItemService;
import com.ppl2.smartshop.services.ProductTagService;
import com.ppl2.smartshop.services.ShopService;

@Controller
public class ProductManagerController {
	@Autowired
	private IProductOptionService optionService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductTagService productTagsService;
	@Autowired
	private ProductItemService productItemService;
	@Autowired
	private ShopService shopService;
	@PostMapping("/shop/options/add")
	private String addCategory(Model model, @RequestParam String feature, @RequestParam String name,
			@RequestParam Long productId) {
		try {
			OptionDTO option = new OptionDTO();
			option.setFeature(feature);
			option.setName(name);
			option.setProductItemId(productId);
			optionService.addOption(option);
			model.addAttribute("message", new Message("Cập nhật thành công", true));
		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("message", new Message("Thêm thất bại", false));
		}
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Shop shop =shopService.getShopByUserId(user.getId());
		List<Category> categories = categoryService.findAll();
		List<ProductTag> productTags = productTagsService.findProductTagByShopId(shop.getId());
		ProductDTO productItem = productItemService.getProductById(productId);
		model.addAttribute("productTags", productTags);
		model.addAttribute("categories", categories);
		model.addAttribute("productItem", productItem);
		return "/administrator/forms-input-product-item";
	}

	@PostMapping("/shop/options/update")
	private String updateCategory(Model model, @RequestParam String feature, @RequestParam String name,
			@RequestParam Long optionId, @RequestParam Long productId) {
		try {
			OptionDTO option = optionService.findById(optionId);
			if (option == null) {
				throw new NullPointerException("Không tìm thấy lựa chọn của sản phẩm này");
			}
			option.setId(optionId);
			option.setFeature(feature);
			option.setName(name);
			option.setProductItemId(productId);
			optionService.save(option);
			model.addAttribute("message", new Message("Cập nhật thành công", true));
		} catch (NullPointerException n) {
			model.addAttribute("message", new Message(n.getMessage(), false));
		} catch (Exception e) {
			model.addAttribute("message", new Message("Cập nhật thất bại", false));
		}
		return "redirect:/shop/manageProductItems/form/" + productId;
	}

	@PostMapping("/shop/options/update-feature")
	private String updateCategory(Model model, @RequestParam String newFeature, @RequestParam String oldFeature, @RequestParam Long productId) {
		try {
			optionService.saveFeature(newFeature, oldFeature,productId);
			model.addAttribute("message", new Message("Cập nhật thành công", true));
		} catch (Exception e) {
			model.addAttribute("message", new Message("Cập nhật thất bại", false));
		}
		return "redirect:/shop/manageProductItems/form/" + productId;
	}

	@PostMapping("/shop/options/delete")
	private String deleteCategory(Model model, @RequestParam Long optionId, @RequestParam Long productId) {
		try {
			optionService.remove(optionId);
			model.addAttribute("message", new Message("Xóa thành công", true));
		} catch (Exception e) {
			model.addAttribute("message", new Message("Xóa thất bại", false));
		}
		return "redirect:/shop/manageProductItems/form/" + productId;
	}
}
