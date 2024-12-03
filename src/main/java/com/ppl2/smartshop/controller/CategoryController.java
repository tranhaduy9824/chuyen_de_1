package com.ppl2.smartshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.Category;
import com.ppl2.smartshop.model.Message;
import com.ppl2.smartshop.services.ICategoryService;

@Controller
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	@GetMapping("/admin/manage-category")
	private String allCategory(Model model ){
		model.addAttribute("categoryList",categoryService.classify());
		return "administrator/manage_category";
	}
	@PostMapping("/admin/manage-category/add")
	private String addCategory(Model model ,@RequestParam List<String> categories){
		try {
			Category category=new Category();
			category.setName(String.join("-", categories));
			categoryService.save(category);
			model.addAttribute("message",new Message("Cập nhật thành công",true));
		}
		catch (Exception e) {
			model.addAttribute("message",new Message("Thêm thất bại",false));
		}
		model.addAttribute("categoryList",categoryService.classify());
		return "administrator/manage_category";
	}
	@PostMapping("/admin/manage-category/update")
	private String updateCategory(Model model ,@RequestParam List<String> categories,@RequestParam Integer categoryId){
		try {
			Category category=categoryService.findById(categoryId);
			if(category==null) {
				throw new NullPointerException("Không tìm thấy danh mục này");
			}
			category.setName(String.join("-", categories));
			categoryService.save(category);
			model.addAttribute("message",new Message("Cập nhật thành công",true));
		}catch(NullPointerException n) {
			model.addAttribute("message",new Message(n.getMessage(),false));
		}
		catch (Exception e) {
			model.addAttribute("message",new Message("Cập nhật thất bại",false));
		}
		model.addAttribute("categoryList",categoryService.classify());
		return "administrator/manage_category";
	}
	@PostMapping("/admin/manage-category/update-type")
	private String updateCategory(Model model ,@RequestParam String newType,@RequestParam String oldType){
		try {
			categoryService.saveType(newType,oldType);
			model.addAttribute("message",new Message("Cập nhật thành công",true));
		}
		catch (Exception e) {
			model.addAttribute("message",new Message("Cập nhật thất bại",false));
		}
		model.addAttribute("categoryList",categoryService.classify());
		return "administrator/manage_category";
	}
	@PostMapping("/admin/manage-category/delete")
	private String deleteCategory(Model model,@RequestParam Integer categoryId ){
		try {
			categoryService.remove(categoryId);
			model.addAttribute("message",new Message("Xóa thành công",true));
		} catch (Exception e) {
			model.addAttribute("message",new Message("Xóa thất bại",false));
		}
		model.addAttribute("categoryList",categoryService.classify());
		return "administrator/manage_category";
	}
}
