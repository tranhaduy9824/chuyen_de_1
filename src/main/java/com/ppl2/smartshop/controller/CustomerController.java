package com.ppl2.smartshop.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.Mode;
import com.ppl2.smartshop.model.AddressDTO;
import com.ppl2.smartshop.model.CustomerDTO;
import com.ppl2.smartshop.model.Message;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.security.service.IUserService;
import com.ppl2.smartshop.services.ICustomerService;
import com.ppl2.smartshop.services.IShopService;

@Controller
@RequestMapping("/user")
public class CustomerController {
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IShopService shopService;
	@GetMapping("/profile")
	private String  profilePage(Model model){
		UserPrinciple user = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("cities", City.values());
		System.out.println(user.getId());
		model.addAttribute("user", customerService.findByUserId(user.getId()));
		return "profile";
	}
	
	@PostMapping("/profile")
	private String  updateProfile(Model model,@ModelAttribute("user")CustomerDTO customerDTO,@RequestParam(required = false,defaultValue = "-1") Long address ){
		List<AddressDTO> addressDTOs=customerDTO.getAddresses();
		
		for(int i=0;i<addressDTOs.size();i++) {
			if(i==address) {
				addressDTOs.get(i).setMode(Mode.DEFAULT);
			}else {
				addressDTOs.get(i).setMode(Mode.OTHER);
			}
		}
		addressDTOs.removeIf(adr->adr.isEmpty());
		model.addAttribute("cities", City.values());
		model.addAttribute("user",customerService.updateProfile(customerDTO));
		return "profile";
	}
	@PostMapping("/profile/new-pasword")
	private String  changePass(Model model,@RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass){
		UserPrinciple user = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", customerService.findByUserId(user.getId()));
		return "profile";
	}
	@GetMapping("/register-shop")
	private String  getPageRegisterShop(Model model){
		UserPrinciple user = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("cities", City.values());
		ShopDTO shopDTO=new ShopDTO();
		shopDTO.setUser(userService.findById(user.getId()));
		model.addAttribute("shop", shopDTO);
		return "register-shop";
	}
	@PostMapping("/register-shop")
	private String  registerShop(Model model,@ModelAttribute("shop")ShopDTO shopDTO  ){
		UserPrinciple user = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("cities", City.values());
		CustomerDTO customerDTO=customerService.findByUserId(user.getId());
		model.addAttribute("user", customerDTO);
		shopDTO.setUserId(customerDTO.getUserId());
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_SHOP"));
		Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
			    authentication.getPrincipal(),
			    authentication.getCredentials(),
			    updatedAuthorities
			);

			SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
		if(customerDTO.equals(null)) {
			model.addAttribute("error",new Message("Không tìm thấy tài khoản người dùng",false));
			model.addAttribute("shop", shopDTO);
			return "register-shop";
		}
		shopDTO=shopService.addShop(shopDTO);
		model.addAttribute("shop", shopDTO);
		return "profile";
	}
}
