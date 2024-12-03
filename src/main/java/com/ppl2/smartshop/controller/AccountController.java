package com.ppl2.smartshop.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.ppl2.smartshop.entities.User;
//import com.ppl2.smartshop.model.AccountDTO;
import com.ppl2.smartshop.model.Message;
import com.ppl2.smartshop.security.repo.IUserRepository;
import com.ppl2.smartshop.security.service.IRoleService;
import com.ppl2.smartshop.security.service.IUserService;

@Controller
public class AccountController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserRepository userRepository;
	
	final String resetPassword="123456";
	
	@GetMapping("/admin/manage-account")
	public String getReporter(Model model,			
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
		    @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
		    @RequestParam(name = "userId", required = false) Long userId) {
		
		//lấy page theo số trang(page) và số item (size) 1 page
		Pageable pagination=PageRequest.of(page-1, size,Sort.by("userId").ascending());
		Page<User> pages=userService.getPageUser(pagination);
		Message message = new Message();
		//kiểm tra xem có tồn tại user có id
		try {
			if(userId!=null) {
				model.addAttribute("user", userService.findById(userId));
			}
		} catch (Exception e) {
			model.addAttribute("user", null);
			message.setStatus(false);
			message.setMessage("Không tìm thấy tài khoản có mã " + userId);
		}
		model.addAttribute("account", new User());
		model.addAttribute("listUser", pages.get().toList());
		model.addAttribute("pages", pages.getTotalPages());
		model.addAttribute("roles", roleService.findAll());
		return "administrator/manage_account";
	}

	@PostMapping("/admin/manage-account/add")
	public ModelAndView addAccount(HttpServletRequest request,RedirectAttributes redir,@ModelAttribute("account") User account) {
		Message message = new Message();
		//kiểm tra thay đổi thành công chưa
		try {
			userService.save(account);
			message.setStatus(true);
			message.setMessage("Thành công thêm tài khoản");
		} catch (Exception e) {
			message.setStatus(false);
			message.setMessage("Thêm tài khoản thất bại");
		}
		//lấy ra previous url và xóa userId
	    String referer = request.getHeader("Referer");
	    String url = referer.replaceFirst("\\buserId=.*?(&|$)", "");
	    
		ModelAndView mv = new ModelAndView("redirect:"+url);
		redir.addFlashAttribute("message", message);
		// trả ra trang manage account
		return mv;
	}
	@PostMapping("/admin/manage-account/update")
	public ModelAndView updateAccount(HttpServletRequest request,RedirectAttributes redir,@ModelAttribute("user") User account) {
		Message message = new Message();
		//kiểm tra thay đổi thành công chưa
		try {
			//form check is false=true or true=false
			account.setLocked(!account.isLocked());
			userRepository.save(account);
			message.setStatus(true);
			message.setMessage("cập nhật tài khoản thành công");
		} catch (Exception e) {
			message.setStatus(false);
			message.setMessage("cập nhật tài khoản thất bại");
		}
	    String referer = request.getHeader("Referer");
		ModelAndView mv = new ModelAndView("redirect:"+referer);
		redir.addFlashAttribute("message", message);
		// trả ra trang manage account
		return mv;
	}
	//reset password do admin tùy chọn 
	@GetMapping("/admin/reset-password/{id}")
	public String resetPassword(@PathVariable Long id) {
		userService.changePassword(id,resetPassword);
		return "redirect:/admin/manage-account";
	}
	@PostMapping("/admin/account/delete")
	public ModelAndView deleteAccount(HttpServletRequest request,RedirectAttributes redir,Model model, @RequestParam("userId") long id) {
		// tạo ra message kiểm tra xem xóa thành công hay thất bại
		Message message = new Message();
		try {
			userService.remove(id);
			message.setStatus(true);
			message.setMessage("Xóa thành công Mã tài khoản " + id);
		} catch (Exception e) {
			message.setStatus(false);
			message.setMessage("Xóa thất bại Mã tài khoản " + id);
		}
		
	    String referer = request.getHeader("Referer");
		ModelAndView mv = new ModelAndView("redirect:"+referer);
		redir.addFlashAttribute("message", message);
		// trả ra trang manage account
		return mv;
	}

}
